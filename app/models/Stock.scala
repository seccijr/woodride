package models

import org.anormcypher._
import types.PriceRel.PriceRelType
import java.util.Date
import types.PriceRel
import play.api.libs.json._

case class Lot(location: TLocation, number: Int, quantity: Int, costPrice: TPrice) extends TLot

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

object StockModelImplicits {
  implicit val rowToLocation = Column.nonNull[TLocation] {
    (value, meta) => {
      val json: JsValue = Json.parse(value.toString)
      json.validate[Location] match {
        case s: JsSuccess[Location] => Right(s.get)
        case x => Left(TypeDoesNotMatch(s"Cannot convert $x: ${x.getClass} to String for column ${meta.column}"))
      }
    }
  }
}

class StockModel extends TStockModel {
  val lotRowDef = s"""
    n.location,
    n.number,
    n.costPrice,
    n.quantity
    """.stripMargin

  def lotRowParser(row: CypherResultRow): TLot= {
    import models.PriceModelImplicits._

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

  override def getByProduct(product: TProduct): TStock = {
    val lots: List[TLot] = Cypher(
      s"""
        |MATCH (p1:Price)-[r1:MAIN_PRICE]->(n:Product {sort: {sort}})
        |WITH p1, r1 ORDER BY r1.date DESC LIMIT 1
        |MATCH (p2:Price)-[r2:COST_PRICE]->(n)
        |WITH p1, p2, r1, r2, n ORDER BY r1.date DESC LIMIT 1
        |RETURN ${lotRowDef}
      """.stripMargin)
      .on("sort" -> "some")()
      .toList.map(lotRowParser)

    Stock(lots)
  }
}
