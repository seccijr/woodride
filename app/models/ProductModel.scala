package models

import org.anormcypher._
import types.PriceRel._
import java.util.Date
import types.Product.ProductType

class Product(val ref: String, val sort: String, val name: String, val pattern: String, val color: String,
              val picture: String, val onSales: Boolean, val mainPrice: TPrice, val date: Date) extends TProduct {
  self: TStockModelComposition =>

  private var _stock: Option[TStock] = None

  override def outOfStock: Boolean = {
    val stock = _stock.getOrElse(stockModel.getByProduct(this))

    stock.quantity <= 0
  }

  override def stock_=(value: TStock): Unit = {
    _stock = Some(value)
  }

  override def stock: TStock = {
    _stock.getOrElse(stockModel.getByProduct(this))
  }
}

class ProductModel extends TProductModel {
  self: TStockModelComposition =>
  implicit val impStockModel = stockModel

  object Product {
    def apply (ref: String, sort: String, name: String, pattern: String, color: String, picture: String,
               onSales: Boolean, mainPrice: TPrice, date: Date): TProduct = {
      new Product(ref, sort, name, pattern, color, picture, onSales, mainPrice, date) with TStockModelComposition {
        val stockModel = implicitly[TStockModel]
      }
    }
  }

  val productRowDef = s"""
    n.ref,
    n.sort,
    n.name,
    n.pattern,
    n.color,
    n.picture,
    n.date,
    p.value,
    p.currency,
    type(r) as priceType,
    r.date
    """.stripMargin

  def productRowParser(row: CypherResultRow): TProduct = {
    import models.PriceModelImplicits._

    val mainPrice: TPrice = Price(
      row[Double]("p.value"),
      row[String]("p.currency"),
      row[PriceRelType]("priceType"),
      row[Date]("r.date")
    )

    Product(
      row[String]("n.ref"),
      row[String]("n.sort"),
      row[String]("n.name"),
      row[String]("n.pattern"),
      row[String]("n.color"),
      row[String]("n.picture"),
      row[Boolean]("n.onSales"),
      mainPrice,
      row[Date]("n.date")
    )
  }

  override def getByName(name: String): TProduct = {
    Cypher(
      s"""
        |MATCH (p:Price)-[r:MAIN_PRICE]->(n:Product {name: {name}})
        |WITH r ORDER BY r.date DESC LIMIT 1
        |MATCH (n:Product)
        |WITH n, r, p
        |RETURN ${productRowDef}
      """.stripMargin)
      .on("name" -> name)().map(productRowParser).head
  }

  override def getByRef(ref: String): TProduct = {
    Cypher(
      s"""
        |MATCH (p:Price)-[r:MAIN_PRICE]->(n:Product {ref: {ref}})
        |WITH r ORDER BY r.date DESC LIMIT 1
        |MATCH (n:Product)
        |WITH n, r, p
        |RETURN ${productRowDef}
      """.stripMargin)
      .on("ref" -> ref)().map(productRowParser).head
  }

  override def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    Cypher(
      s"""
        |MATCH (p:Price)-[r:MAIN_PRICE]->(n:Product {sort: {sort}})
        |WITH r ORDER BY r.date DESC LIMIT 1
        |MATCH (n:Product)
        |WITH n, r, p
        |RETURN ${productRowDef}
        |ORDER BY n.date DESC
        |SKIP {skip}
        |LIMIT {limit}
      """.stripMargin)
      .on("limit" -> pageSize, "skip" -> (page - 1) * pageSize, "sort" -> productType.toString)()
      .toList.map(productRowParser)
  }

  override def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    Cypher(
      s"""
        |MATCH (p:Price)-[r:MAIN_PRICE]->(n:Product {sort: {sort}})
        |WITH p, r ORDER BY r.date DESC LIMIT 1
        |MATCH (n)-[:OBJECT_OF]->(s:Sale)
        |WITH n, r, p, s
        |RETURN ${productRowDef}, count(s) as n_sales
        |ORDER BY n_sales DESC
        |SKIP {skip}
        |LIMIT {limit}
      """.stripMargin)
      .on("limit" -> pageSize, "skip" -> (page - 1) * pageSize, "sort" -> productType.toString)()
      .toList.map(productRowParser)
  }
}
