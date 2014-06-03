package models

import org.anormcypher._
import types.PriceRel.PriceRelType
import java.util.Date
import types.PriceRel

class Lot(val number: Int, val quantity: Int, val costPrice: TPrice) extends TLot {
  self: TLocationModelComposition =>

  private var _locations: List[TLocation] = Nil

  override def locations: List[TLocation] = {
    if (_locations.isEmpty) _locations = locationModel.getByLot(this)
    _locations
  }

  override def locations_=(value: List[TLocation]): Unit = {
    _locations = value
  }
}

case class Stock (lots: List[TLot]) extends TStock {
  override def averageCost: TPrice = {
    val total = lots.foldLeft(0.0) { (sum:Double, e:TLot) =>
      sum + e.costPrice.value
    }
    val currency = lots.head.costPrice.currency

    Price(total / quantity, currency, PriceRel.COST_PRICE, new Date)
  }

  override def quantity: Int = lots.length
}

class StockModel extends TStockModel {
  self: TLocationModelComposition =>

  implicit val impLocationModel = locationModel

  object Lot {
    def apply (number: Int, quantity: Int, costPrice: TPrice): TLot = {
      new Lot(number, quantity, costPrice) with TLocationModelComposition {
        val locationModel = implicitly[TLocationModel]
      }
    }
  }

  val lotRowDef = s"""
    lot.number,
    lot.quantity,
    price.value,
    price.currency,
    type(r) as priceType,
    r.date
      """.stripMargin

  def lotRowParser(row: CypherResultRow): TLot= {
    import models.PriceModelImplicits._

    val costPrice: TPrice = Price(
      row[Double]("price.value"),
      row[String]("price.currency"),
      row[PriceRelType]("priceType"),
      row[Date]("r.date")
    )

    Lot(
      row[Int]("n.number"),
      row[Int]("n.quantity"),
      costPrice
    )
  }

  override def getByProduct(product: TProduct): TStock = {
    val lots: List[TLot] = Cypher(
      s"""
        |MATCH (pr:Product{ref: {ref}}),
        |(pr)<-[:LOT_OF]-(lot:Lot)-[r:LOCATED_AT]->(loc)-[:LOCATION_IN]->(cp:PostalCode),
        |(price:Price)<-[:COST_PRICE]-(lot),
	      |(loc)-[:LOCATION_IN]->(st:Street)-[:STREET_OF]->(ci:City)-[:CITY_OF]->(co:Country),
	      |(cp)-[:POSTAL_CODE_OF]->(ci)
        |RETURN ${lotRowDef}
      """.stripMargin)
      .on("ref" -> product.ref)()
      .toList.map(lotRowParser)

    Stock(lots)
  }
}
