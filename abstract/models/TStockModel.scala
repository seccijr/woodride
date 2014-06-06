package models

trait TStockModelComposition {
  val stockModel: TStockModel
}

trait TStockModel {
  def getByProduct(product: TProduct): TStock
}

trait TStock {
  val lots: List[TLot]
  def quantity: Int
  def averageCost: TPrice
}
