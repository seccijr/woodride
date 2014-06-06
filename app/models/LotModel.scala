package models

import repositories.TLotRepositoryComposition

class Lot(val number: Int, val costPrice: TPrice) extends TLot {
  self: TLocationModelComposition with TLotModelComposition =>

  private var _locations: List[TLocation] = Nil
  private var _quantity: Option[Int] = None

  private def loadQuantity: Int = {
    val quantity = lotModel.getQuantity(this)
    _quantity = Some(quantity)
    quantity
  }

  override def locations: List[TLocation] = {
    if (_locations.isEmpty) _locations = locationModel.getByLot(this)
    _locations
  }

  override def locations_=(value: List[TLocation]): Unit = {
    _locations = value
  }

  override def quantity: Int = {
    _quantity.getOrElse(loadQuantity)
  }
}

class LotModel extends TLotModel {
  self: TLotRepositoryComposition =>

  override def getQuantity(lot: TLot): Int = {
    lotRepository.getQuantityByLot(lot)
  }
}
