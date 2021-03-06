package models

import java.util.Date
import types.PriceRel
import repositories.TLotRepositoryComposition
import factories.TStockFactoryComposition

class Stock(override val lots: List[TLot]) extends TStock {
  override def averageCost: TPrice = {
    val total = lots.foldLeft(0.0) { (sum: Double, e: TLot) =>
      sum + e.costPrice.value
    }
    val currency = lots.head.costPrice.currency

    Price(total / quantity, currency, PriceRel.COST_PRICE, new Date)
  }

  override def quantity: Int = lots.length
}

class StockModel extends TStockModel {
  self: TLotRepositoryComposition with TStockFactoryComposition =>

  override def getByProduct(product: TProduct): Option[TStock] = {
    val lots = lotRepository.getByProduct(product)
    Some(stockFactory(lots))
  }
}
