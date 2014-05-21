package services

import models.{TProductModelComposition, TProduct}
import types.Product.ProductType

class ProductService extends TProductService {
  self: TProductModelComposition =>

  override def getByName(name: String): TProduct = {
    productModel.getByName(name)
  }

  override def getByRef(ref: String): TProduct = {
    productModel.getByRef(ref)
  }

  override def getCatalog(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productModel.getCatalog(page, pageSize, productType)
  }
}
