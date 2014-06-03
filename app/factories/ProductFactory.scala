package factories

import models._
import java.util.Date

class ProductFactory extends TProductFactory {
  self: TStockModelComposition =>

  implicit val impStockModel = stockModel

  def apply (ref: String, sort: String, name: String, pattern: String, color: String, picture: String,
             onSales: Boolean, mainPrice: TPrice, date: Date): TProduct = {
    new Product(ref, sort, name, pattern, color, picture, onSales, mainPrice, date) with TStockModelComposition {
      val stockModel = implicitly[TStockModel]
    }
  }
}

