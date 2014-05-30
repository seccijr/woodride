package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class Country (name: String) extends TCountry
case class City (name: String) extends TCity
case class Street (name: String, streetType: String) extends TStreet
case class Location (details: String, street: TStreet, postalCode: String,
                     city: TCity, country: TCountryModel) extends TLocation


object LocationModelImplicits {
  implicit val streetReads: Reads[Street] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "type").read[String]
    )(Street.apply _)
  implicit val cityReads: Reads[City] = (JsPath \ "name").read[City]
  implicit val countryReads: Reads[Country] = (JsPath \ "name").read[Country]
  implicit val locationReads: Reads[Location] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "street").read[Double](min(-90.0) keepAnd max(90.0)) and
    )(Location.apply _)
}
