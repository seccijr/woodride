package models

trait TLocationModel {
  def getByCountry(country: TCountry): TLocation
}

trait TLocation {
  val details: String
  val street: TStreet
  val postalCode: String
  val city: TCityModel
  val country: TCountryModel
}
