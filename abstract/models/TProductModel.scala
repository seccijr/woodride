package models

import types.Product.ProductType

trait  TProductModelComposition {
  val productModel: TProductModel
}

trait TProductModel {
  def getByName(name: String): TProduct
  def getByRef(ref: String): TProduct
  def getCatalog(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
}

trait TProduct {
  val ref: String
  val sort: String
  val name: String
  val pattern: String
  val color: String
  val picture: String
  val onSales: Boolean
  val mainPrice: TPrice
  val costPrice: TPrice
  def stock: List[TStock]
  def stock_=(value: List[TStock]): Unit
  def outOfStock: Boolean
}
