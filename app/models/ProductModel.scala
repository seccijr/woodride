package models

import java.util.Date
import types.Product.ProductType
import repositories.TProductRepositoryComposition

class Product(val ref: String, val sort: String, val name: String, val pattern: String, val color: String,
              val picture: String, val onSales: Boolean, val mainPrice: TPrice, val date: Date) extends TProduct {
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
