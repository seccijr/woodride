package models

trait TStockModelComposition {
  val stockModel: TStockModel
}

trait TStockModel {
  def getByProduct(product: TProduct): TStock
}

trait TLot {
  val number: Int
  val costPrice: TPrice
  def quantity: Int
  def locations: List[TLocation]
  def locations_=(value: List[TLocation]): Unit
}

trait TStock {
  val lots: List[TLot]
  def quantity: Int
  def averageCost: TPrice
}
