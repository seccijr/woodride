package models

import types.Product.ProductType
import java.util.Date

trait  TProductModelComposition {
  val productModel: TProductModel
}

trait TProductModel {
  def getByName(name: String): Option[TProduct]
  def getByRef(ref: String): Option[TProduct]
  def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
  def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
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
  val date: Date
  def stock: Option[TStock]
  def stock_=(value: Option[TStock]): Unit
  def outOfStock: Boolean
}
