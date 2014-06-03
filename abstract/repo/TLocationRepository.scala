package repo

import models.{TLot, TCountry, TLocation}

trait TLocationRepositoryComposition {
  val locationRepository: TLocationRepository
}

trait TLocationRepository {
  def getByCountry(country: TCountry): List[TLocation]
  def getByLot(lot: TLot): List[TLocation]
}
