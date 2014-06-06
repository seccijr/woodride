package models

import java.util.Date
import types.Product.ProductType
import repositories.TProductRepositoryComposition

class Product(val ref: String, val sort: String, val name: String, val pattern: String, val color: String,
              val picture: String, val onSales: Boolean, val mainPrice: TPrice, val date: Date) extends TProduct {
  self: TStockModelComposition =>

  private var _stock: Option[TStock] = None

  private def loadStock: TStock = {
    val stock = stockModel.getByProduct(this)
    _stock = Some(stock)
    stock
  }

  override def outOfStock: Boolean = {
    _stock.getOrElse(loadStock).quantity <= 0
  }

  override def stock_=(value: TStock): Unit = {
    _stock = Some(value)
  }

  override def stock: TStock = {
    _stock.getOrElse(loadStock)
  }
}

class ProductModel extends TProductModel {
  self: TProductRepositoryComposition =>

  override def getByName(name: String): TProduct = {
    productRepository.getByName(name)
  }

  override def getByRef(ref: String): TProduct = {
    productRepository.getByRef(ref)
  }

  override def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productRepository.getNewArrivals(page, pageSize, productType)
  }

  override def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productRepository.getBestSeller(page, pageSize, productType)
  }
}
