package models

import org.anormcypher._
import types._
import types.PriceRel._
import java.util.Date

case class Price(value: Double, currency: String, relType: PriceRelType, date: Date) extends TPrice



object PriceModelImplicits {
  implicit val rowToPriceRelType = Column.nonNull[PriceRelType] {
    (value, meta) => {
      value match {
        case s: String=> Right(PriceRel.withName(s))
        case x => Left(TypeDoesNotMatch(s"Cannot convert $x: ${x.getClass} to String for column ${meta.column}"))
      }
    }
  }

  implicit def rowToDate = Column.nonNull[Date] {
    (value, meta) => value match {
      case s: BigDecimal => Right(new Date(s.toLong))
      case x => Left(TypeDoesNotMatch(s"Cannot convert $x: ${x.getClass} to String for column ${meta.column}"))
    }
  }
}

class PriceModel extends TPriceModel {
  import models.PriceModelImplicits._
  override def getRelatedToProduct(ref: String): List[TPrice] = {
    Cypher(
      """
        |MATCH (n:Price)-[r:SALE_PRICE]->(p:Product {ref: {ref}})
        |RETURN n.value, n.currency, type(r) as priceType, r.date as priceDate
      """.stripMargin)
      .on("ref" -> ref)().map {
      row => Price(
        row[Double]("n.value"),
        row[String]("n.currency"),
        row[PriceRelType]("p.type"),
        row[Date]("priceDate")
      )
    }.toList
  }
}
