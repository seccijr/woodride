package repo

import models.TProduct
import types.Product.ProductType

trait TProductRepositoryComposition {
  val productRepository: TProductRepository
}

trait TProductRepository {
  def getByName(name: String): TProduct
  def getByRef(ref: String): TProduct
  def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
  def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
}
