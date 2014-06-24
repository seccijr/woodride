package repositories

import java.util.Date
import org.anormcypher.{Cypher, CypherResultRow}
import models._
import types.PriceRel.PriceRelType
import factories.TLotFactoryComposition

class LotRepository extends TLotRepository {
  self: TLotFactoryComposition =>

  val lotRowDef = s"""
    lot.number,
    price.value,
    price.currency,
    type(r) as priceType,
    r.date
    """.stripMargin

  def lotRowParser(row: CypherResultRow): TLot = {
    import models.PriceModelImplicits._

    val costPrice: TPrice = Price(
      row[Double]("price.value"),
      row[String]("price.currency"),
      row[PriceRelType]("priceType"),
      row[Date]("r.date")
    )

    lotFactory(
      row[Int]("lot.number"),
      costPrice
    )
  }

  override def getByProduct(product: TProduct): List[TLot] = {
    Cypher(
      s"""
        |MATCH (pr:Product{ref: {ref}}),
        |(pr)<-[:LOT_OF]-(lot:Lot)-[:LOCATED_AT]->(loc)-[:LOCATION_IN]->(cp:PostalCode),
        |(price:Price)<-[r:COST_PRICE]-(lot),
	      |(loc)-[:LOCATION_IN]->(st:Street)-[:STREET_OF]->(ci:City)-[:CITY_OF]->(co:Country),
	      |(cp)-[:POSTAL_CODE_OF]->(ci)
        |RETURN ${lotRowDef}
      """.stripMargin)
      .on("ref" -> product.ref)()
      .toList.map(lotRowParser)
  }

  override def getQuantityByLot(lot: TLot): Int = {
    Cypher(
      s"""
        |MATCH (lot:Lot{number: {number}}),
        |(lot)-[r:LOCATED_AT]->(loc)
        |RETURN sum(r.quantity) as quantity
      """.stripMargin)
      .on("number" -> lot.number)()
      .toList.foldLeft(0)((sum, row) => (sum + row[Int]("quantity")))
  }
}