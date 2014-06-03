package models

import repo.TLocationRepositoryComposition

case class Country (name: String) extends TCountry
case class City (name: String) extends TCity
case class Street (name: String, streetType: String) extends TStreet
case class Location (details: String, street: TStreet, postalCode: String, city: TCity, country: TCountry)
  extends TLocation

class LocationModel extends TLocationModel {
  self: TLocationRepositoryComposition =>

  override def getByCountry(country: TCountry): List[TLocation] = {
    locationRepository.getByCountry(country)
  }

  override def getByLot(lot: TLot): List[TLocation] = {
    locationRepository.getByLot(lot)
  }
}
