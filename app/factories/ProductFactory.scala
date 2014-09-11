package factories

import models._
import java.util.Date

class ProductFactory(val stockModelInstance: TStockModel) extends TProductFactory {
  override def apply (
                       ref: String,
                       sort: String,
                       name: String,
                       pattern: String,
                       color: String,
                       picture: String,
                       onSales: Boolean,
                       mainPrice: TPrice,
                       date: Date
                       ): TProduct = {
    new Product(ref, sort, name, pattern, color, picture, onSales, mainPrice, date)  with TStockModelComposition {
      val stockModel = stockModelInstance
    }
  }
}

