package factories

import models.{TLot, TStock}

trait TStockFactoryComposition {
  val stockFactory: TStockFactory
}

trait TStockFactory {
  def apply(lots: List[TLot]): TStock
}
