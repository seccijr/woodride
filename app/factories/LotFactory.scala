package factories

import models._

class LotFactory extends TLotFactory {
  self: TLocationModelComposition =>

  implicit val impLocationModel = locationModel

  def apply (number: Int, quantity: Int, costPrice: TPrice): TLot = {
    new Lot(number, quantity, costPrice) with TLocationModelComposition {
      val locationModel = implicitly[TLocationModel]
    }
  }
}

