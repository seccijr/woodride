package repositories

import java.util.Date
import org.anormcypher.{Cypher, CypherResultRow}
import models.{Price, TPrice, TProduct}
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
    l.color,
    l.picture,
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
      row[String]("l.color"),
      row[String]("l.picture"),
      row[Boolean]("n.onSales"),
      mainPrice,
      row[Date]("n.date")
    )
  }

  override def getByName(name: String): Option[TProduct] = {
    Cypher(
      s"""
        |MATCH (n:Product {name: {name}})<-[:DEMO_LOT_OF]-(l:Lot)-[r:SALE_PRICE]->(p:Price)
        |RETURN ${productRowDef}
        |ORDER BY r.date DESC LIMIT 1
      """.stripMargin)
      .on("name" -> name)().headOption.map(productRowParser)
  }

  override def getByRef(ref: String): Option[TProduct] = {
    Cypher(
      s"""
        |MATCH (n:Product {ref: {ref}})<-[:DEMO_LOT_OF]-(l:Lot)-[r:SALE_PRICE]->(p:Price)
        |RETURN ${productRowDef}
        |ORDER BY r.date DESC LIMIT 1
      """.stripMargin)
      .on("ref" -> ref)().headOption.map(productRowParser)
  }

  override def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    Cypher(
      s"""
        |MATCH (n:Product)<-[:LOT_OF]-(:Lot)-[sp:SALE_PRICE]->(:Price)
        |WITH n, max(sp.date) as newest
        |MATCH (n)<-[:DEMO_LOT_OF]-(l:Lot)-[r:SALE_PRICE {date: newest}]->(p:Price)
        |WITH n, r, p, l
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
        |MATCH (n:Product)<-[:LOT_OF]-(:Lot)-[sp:SALE_PRICE]->(:Price)
        |WITH n, max(sp.date) as newest
        |MATCH (n)<-[:DEMO_LOT_OF]-(l:Lot)-[r:SALE_PRICE {date: newest}]->(p:Price), (l)-[:OBJECT_OF]->(s:Sale)
        |WITH n, r, p, s, l
        |RETURN ${productRowDef}, count(s) as n_sales
        |ORDER BY n_sales DESC
        |SKIP {skip}
        |LIMIT {limit}
      """.stripMargin)
      .on("limit" -> pageSize, "skip" -> (page - 1) * pageSize, "sort" -> productType.toString)()
      .toList.map(productRowParser)
  }
}
