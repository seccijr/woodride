package repositories

import java.util.Date
import org.anormcypher.{Cypher, CypherResultRow}
import models.{TStockModelComposition, Price, TPrice, TProduct}
import types.PriceRel.PriceRelType
import types.Product.ProductType
import factories.TProductFactoryComposition

class ProductRepository extends TProductRepository {
  self: TProductFactoryComposition =>

  val productRowDef = s"""
    n.ref,
    n.sort,
    n.name,
    n.pattern,
    n.color,
    n.picture,
    n.date,
    n.onSales,
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

    productFactory(
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

  override def getByName(name: String): Option[TProduct] = {
    Cypher(
      s"""
        |MATCH (p:Price)-[r:MAIN_PRICE]->(n:Product {name: {name}})
        |RETURN ${productRowDef}
        |ORDER BY r.date DESC LIMIT 1
      """.stripMargin)
      .on("name" -> name)().headOption.map(productRowParser)
  }

  override def getByRef(ref: String): Option[TProduct] = {
    Cypher(
      s"""
        |MATCH (p:Price)<-[r:MAIN_PRICE]-(n:Product {ref: {ref}})
        |RETURN ${productRowDef}
        |ORDER BY r.date DESC LIMIT 1
      """.stripMargin)
      .on("ref" -> ref)().headOption.map(productRowParser)
  }

  override def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    Cypher(
      s"""
        |MATCH (p:Price)<-[r:MAIN_PRICE]-(n:Product {sort: {sort}})
        |WITH r, p ORDER BY r.date DESC LIMIT 1
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
        |MATCH (p:Price)<-[r:MAIN_PRICE]-(n:Product {sort: {sort}})
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
