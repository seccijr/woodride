package models

import java.util.Date
import types.Product.ProductType
import repositories.TProductRepositoryComposition

class Product(
               override val ref: String,
               override val sort: String,
               override val name: String,
               override val pattern: String,
               override val color: String,
               override val picture: String,
               override val onSales: Boolean,
               override val mainPrice: TPrice,
               override val date: Date
               ) extends TProduct {
  self: TStockModelComposition =>

  private var _stock: Option[TStock] = None
  private var _stockLoaded: Boolean = false

  private def getStock: Option[TStock] = {
    if (!_stockLoaded) {
      _stock = stockModel.getByProduct(this)
      _stockLoaded = true
    }
    _stock
  }

  override def outOfStock: Boolean = {
    getStock match {
      case Some(s) => s.quantity <= 0
      case None => false
    }
  }

  override def stock_=(value: Option[TStock]): Unit = {
    _stock = value
  }

  override def stock: Option[TStock] = {
    getStock
  }
}

class ProductModel extends TProductModel {
  self: TProductRepositoryComposition =>

  override def getByName(name: String): Option[TProduct] = {
    productRepository.getByName(name)
  }

  override def getByRef(ref: String): Option[TProduct] = {
    productRepository.getByRef(ref)
  }

  override def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productRepository.getNewArrivals(page, pageSize, productType)
  }

  override def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productRepository.getBestSeller(page, pageSize, productType)
  }
}
