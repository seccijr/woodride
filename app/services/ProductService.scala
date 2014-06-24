package services

import models.{TProductModelComposition, TProduct}
import types.Product.ProductType

class ProductService extends TProductService {
  self: TProductModelComposition =>

  override def getByName(name: String): Option[TProduct] = {
    productModel.getByName(name)
  }

  override def getByRef(ref: String): Option[TProduct] = {
    productModel.getByRef(ref)
  }

  override def getNewArrivals(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productModel.getNewArrivals(page, pageSize, productType);
  }

  override def getBestSeller(page: Int, pageSize: Int, productType: ProductType): List[TProduct] = {
    productModel.getBestSeller(page, pageSize, productType);
  }
}
