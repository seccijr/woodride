package factories

import models.{TLot, TPrice}

trait TLotFactoryComposition {
  val lotFactory: TLotFactory
}

trait TLotFactory {
  def apply (number: Int, costPrice: TPrice): TLot
}

