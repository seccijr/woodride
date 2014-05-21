package services

import models.TProduct
import types.Product.ProductType

trait TProductServiceComposition {
  val productService: TProductService
}

trait TProductService {
  def getByName(name: String): TProduct
  def getByRef(ref: String): TProduct
  def getCatalog(page: Int, pageSize: Int, productType: ProductType): List[TProduct]
}
