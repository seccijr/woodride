package models

trait TStockModelComposition {
  val stockModel: TStockModel
}

trait TStockModel {
  def getByProduct(product: TProduct): TStock
}

trait TLot {
  val location: TLocation
  val number: Int
  val quantity: Int
  val costPrice: TPrice
}

trait TStock {
  val lots: List[TLot]
  def quantity: Int
  def averageCost: TPrice
}
