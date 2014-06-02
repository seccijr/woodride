package models

import org.anormcypher._
import types.PriceRel._
import java.util.Date
import types.Product.ProductType

case class Country (name: String) extends TCountry
case class City (name: String) extends TCity
case class Street (name: String, streetType: String) extends TStreet
case class Location (details: String, street: TStreet, postalCode: String,
                     city: TCity, country: TCountry) extends TLocation

class LocationModel extends TLocationModel {

  def locationRowParser(row: CypherResultRow): TLocation = {
    val country = Country(row[String]("co.name"))
    val city = City(row[String]("ci.name"))
    val street = Street(row[String]("st.name"), row[String]("st.type"))

    Location(
      row[String]("loc.details"),
      street,
      row[String]("cp.number"),
      city,
      country
    )
  }

  val locationRowDef = s"""
    loc.details,
    labels(loc) as locationType,
    cp.number,
    st.name,
    st.type,
    ci.name,
    co.name""".stripMargin

  override def getByCountry(country: TCountry): List[TLocation] = {
    Nil
  }

  override def getByLot(lot: TLot): List[TLocation] = {
    Cypher(
      s"""
        |MATCH (lot:Lot{number:{number}})-[r:LOCATED_AT]->(loc)-[:LOCATION_IN]->(cp:PostalCode),
	      |(loc)-[:LOCATION_IN]->(st:Street)-[:STREET_OF]->(ci:City)-[:CITY_OF]->(co:Country),
	      |(cp)-[:POSTAL_CODE_OF]->(ci)
        |RETURN ${locationRowDef}
      """.stripMargin)
      .on("number" -> lot.number)()
      .toList.map(locationRowParser)
  }
}
