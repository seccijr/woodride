package models

import types.PriceRel.PriceRelType
import java.util.Date


trait TPriceModelComposition {
  val priceModel: TPriceModel
}

trait TPriceModel {
  def getRelatedToProduct(ref: String): List[TPrice]
}

trait TPrice {
  val value: Double
  val currency: String
  val relType: PriceRelType
  val date: Date
}
