package factories

import models.{Stock, TStock, TLot}

class StockFactory extends TStockFactory {
  override def apply(lots: List[TLot]): TStock = {
    new Stock(lots)
  }
}
