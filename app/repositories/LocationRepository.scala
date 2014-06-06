package repositories

import org.anormcypher.{Cypher, CypherResultRow}
import models._

class LocationRepository extends TLocationRepository {

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
