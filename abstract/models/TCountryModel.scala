package models

trait TCountryModel {
  def getByName(name: String): TCountry
}

trait TCountry {
  val name: String
}
