package models

import java.util.Date
import types.PriceRel
import repo.TLotRepositoryComposition

class Lot(val number: Int, val quantity: Int, val costPrice: TPrice) extends TLot {
  self: TLocationModelComposition =>

  private var _locations: List[TLocation] = Nil

  override def locations: List[TLocation] = {
    if (_locations.isEmpty) _locations = locationModel.getByLot(this)
    _locations
  }

  override def locations_=(value: List[TLocation]): Unit = {
    _locations = value
  }
}

case class Stock(lots: List[TLot]) extends TStock {
  override def averageCost: TPrice = {
    val total = lots.foldLeft(0.0) { (sum:Double, e:TLot) =>
      sum + e.costPrice.value
    }
    val currency = lots.head.costPrice.currency

    Price(total / quantity, currency, PriceRel.COST_PRICE, new Date)
  }

  override def quantity: Int = lots.length
}

class StockModel extends TStockModel {
  self: TLotRepositoryComposition =>

  override def getByProduct(product: TProduct): TStock = {
    val lots = lotRepository.getByProduct(product)
    Stock(lots)
  }
}
