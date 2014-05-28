package models

import org.anormcypher._
import types.PriceRel._
import java.util.Date
import types.Product.ProductType

case class Product(
                    ref: String,
                    sort: String,
                    name: String,
                    pattern: String,
                    color: String,
                    picture: String,
                    onSales: Boolean,
                    mainPrice: TPrice,
                    costPrice: TPrice
                    ) extends TProduct {
  self: TStockModelComposition =>

  private var _stock: List[TStock] = Nil

  override def outOfStock: Boolean = {
    val current = stock reduceLeft  { (sum: Int, e: TStock) =>
      sum + e.quantity
    }

    current > 0
  }

  override def stock_=(value: List[TStock]): Unit = {
    _stock = value
  }

  override def stock: List[TStock] = {
    if (_stock.isEmpty) _stock = stockModel.getByProduct(this)
    _stock
  }
}

class ProductModel extends TProductModel {
  import models.PriceModelImplicits._

  val productRowDef = s"""
    n.ref,
    n.sort,
    n.name,
    n.pattern,
    n.color,
    n.picture,
    p1.value,
    p1.currency,
    type(r1) as price1Type,
    r1.date,
    p2.value,
    p2.currency,
    type(r2) as price2Type,
    r2.date
    """.stripMargin

  def productRowParser(row: CypherResultRow): TProduct = {
    val mainPrice: TPrice = Price(
      row[Double]("p1.value"),
      row[String]("p1.currency"),
      row[PriceRelType]("price1Type"),
      row[Date]("r1.date")
    )
    val costPrice: TPrice = Price(
      row[Double]("p2.value"),
      row[String]("p2.currency"),
      row[PriceRelType]("price2Type"),
      row[Date]("r2.date")
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
      costPrice
    )
  }

  override def getByName(name: String): TProduct = {
    Cypher(
      s"""
        |MATCH (p1:Price)-[r1:MAIN_PRICE]->(n:Product {name: {name}})
        |WITH p1, r1 ORDER BY r1.date DESC LIMIT 1
        |MATCH (p2:Price)-[r2:COST_PRICE]->(n)
        |WITH p1, p2, r1, r2, n ORDER BY r1.date DESC LIMIT 1
        |RETURN ${productRowDef}
      """.stripMargin)
      .on("name" -> name)().map(productRowParser).head
  }

  override def getByRef(ref: String): TProduct = {
    Cypher(
      s"""
        |MATCH (p1:Price)-[r1:MAIN_PRICE]->(n:Product {ref: {ref}})
        |WITH p1, r1 ORDER BY r1.date DESC LIMIT 1
        |MATCH (p2:Price)-[r2:COST_PRICE]->(n)
        |WITH p1, p2, r1, r2, n ORDER BY r1.date DESC LIMIT 1
        |RETURN ${productRowDef}
      """.stripMargin)
      .on("ref" -> ref)().map(productRowParser).head
  }

  override def getNewArrivals(page: Int, pageSize: Int, productTpe: ProductType): List[TProduct] = {
    Cypher(
      s"""
        |MATCH (p1:Price)-[r1:MAIN_PRICE]->(n:Product {sort: {sort}})
        |WITH p1, r1 ORDER BY r1.date DESC LIMIT 1
        |MATCH (p2:Price)-[r2:COST_PRICE]->(n)
        |WITH p1, p2, r1, r2, n ORDER BY r1.date DESC LIMIT 1
        |RETURN ${productRowDef}
      """.stripMargin)
      .on("sort" -> productTpe.toString)().toList.map(productRowParser)
  }

    override def getBestSeller(page: Int, pageSize: Int, productTpe: ProductType): List[TProduct] = {
      Cypher(
        s"""
        |MATCH (p1:Price)-[r1:MAIN_PRICE]->(n:Product {sort: {sort}})
        |WITH p1, r1 ORDER BY r1.date DESC LIMIT 1
        |MATCH (p2:Price)-[r2:COST_PRICE]->(n)
        |WITH p1, p2, r1, r2, n ORDER BY r1.date DESC LIMIT 1
        |RETURN ${productRowDef}
      """.stripMargin)
        .on("sort" -> productTpe.toString)().toList.map(productRowParser)
    }
}
