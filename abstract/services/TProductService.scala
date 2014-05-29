package services

import models.TProduct
import types.Product.ProductType

trait TProductServiceComposition {
  val productService: TProductService
}

trait TProductService {
  def getByName(name: String): TProduct
  def getByRef(ref: String): TProduct
  def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
  def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
}
