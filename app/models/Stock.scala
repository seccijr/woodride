package models

import org.anormcypher.{CypherResultRow, Cypher}
import types.PriceRel.PriceRelType
import java.util.Date

case class Lot(
                location: TLocation,
                number: Int,
                quantity: Int,
                costPrice: TPrice
                ) extends TLot

class StockModel extends TStockModel {
  val lotRowDef = s"""
    n.location,
    n.number,
    n.costPrice,
    n.quantity
    """.stripMargin

  def lotRowParser(row: CypherResultRow): TLot= {
    val costPrice: TPrice = Price(
      row[Double]("p.value"),
      row[String]("p.currency"),
      row[PriceRelType]("priceType"),
      row[Date]("r.date")
    )

    Lot(
      row[TLocation]("n.ref"),
      row[Int]("n.number"),
      row[Int]("n.quantity"),
      costPrice
    )
  }

  def stockFactory(lots: List[TLot]): TStock = {
  }

  override def getByProduct(product: TProduct): List[TStock] = {
    Cypher(
      s"""
        |MATCH (p1:Price)-[r1:MAIN_PRICE]->(n:Product {sort: {sort}})
        |WITH p1, r1 ORDER BY r1.date DESC LIMIT 1
        |MATCH (p2:Price)-[r2:COST_PRICE]->(n)
        |WITH p1, p2, r1, r2, n ORDER BY r1.date DESC LIMIT 1
        |RETURN ${lotRowDef}
      """.stripMargin)
      .on("sort" -> productTpe.toString)().toList.map(lotRowParser)
  }
}
