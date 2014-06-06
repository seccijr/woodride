package factories

import models.{TStockModel, TPrice, TProduct}
import java.util.Date

trait TProductFactoryComposition {
  val productFactory: TProductFactory
}

trait TProductFactory {
  def apply (ref: String, sort: String, name: String, pattern: String, color: String, picture: String,
             onSales: Boolean, mainPrice: TPrice, date: Date): TProduct
}
