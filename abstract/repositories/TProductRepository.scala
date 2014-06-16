package repositories

import models.TProduct
import types.Product.ProductType

trait TProductRepositoryComposition {
  val productRepository: TProductRepository
}

trait TProductRepository {
  def getByName(name: String): Option[TProduct]
  def getByRef(ref: String): Option[TProduct]
  def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
  def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
}
