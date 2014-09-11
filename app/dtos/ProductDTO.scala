package dtos

import models.TProduct

case class ProductDTO(
                       override val ref: String,
                       override val sort: String,
                       override val name: String,
                       override val color: String,
                       override val picture: String,
                       override val onSales: Boolean,
                       override val mainPrice: String,
                       override val outOfStock: Boolean
                       ) extends TProductDTO

object ProductDTO {
  implicit def fromModelToDTO(product: TProduct): TProductDTO = {
    ProductDTO(
      product.ref,
      product.sort,
      product.name,
      product.color,
      product.picture,
      product.onSales,
      product.mainPrice.value + " " + product.mainPrice.currency,
      product.outOfStock
    )
  }

  implicit def fromModelListToDTOList(productList: List[TProduct]): List[TProductDTO] = {
    productList.map((product: TProduct) => fromModelToDTO(product))
  }
}
