package models

trait  TLocationModelComposition {
  val locationModel: TLocationModel
}

trait TLocationModel {
  def getByCountry(country: TCountry): List[TLocation]
  def getByLot(lot: TLot): List[TLocation]
}

trait TLocation {
  val details: String
  val street: TStreet
  val postalCode: String
  val city: TCity
  val country: TCountry
}
