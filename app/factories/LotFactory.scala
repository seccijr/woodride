package factories

import models._

class LotFactory(val locationModelInstance: TLocationModel, val lotModelInstance: TLotModel) extends TLotFactory {
  override def apply (number: Int, costPrice: TPrice): TLot = {
    new Lot(number, costPrice) with TLocationModelComposition with TLotModelComposition {
      val lotModel = lotModelInstance
      val locationModel = locationModelInstance
    }
  }
}

