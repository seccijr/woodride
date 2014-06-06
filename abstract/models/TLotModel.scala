package models

trait TLotModelComposition {
  val lotModel: TLotModel
}

trait TLotModel {
  def getQuantity(lot: TLot): Int
}

trait TLot {
  val number: Int
  val costPrice: TPrice
  def quantity: Int
  def locations: List[TLocation]
  def locations_=(value: List[TLocation]): Unit
}

