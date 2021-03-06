package controllers

import play.api.mvc.Action
import play.api.Play
import types.Product._
import services.TProductServiceComposition

class ProductController extends TProductController {
  self: TProductServiceComposition  =>

  import dtos.ProductDTO._

  override def catalog(pageParam: Option[Int], pageSizeParam: Option[Int]) = Action {
    val defaultPageSize = Play.current.configuration.getString("page.pageSize")
    val page = pageParam.getOrElse[Int](1)
    val pageSize = pageSizeParam.getOrElse[Int](defaultPageSize.get.toInt)
    val newArrivals = productService.getNewArrivals(page, pageSize, SHIRT)
    val bestSeller = productService.getBestSeller(page, pageSize, SHIRT)

    Ok(views.html.product.catalog(newArrivals, bestSeller))
  }

  override def details(refParam: String) = Action {
    productService.getByRef(refParam) match {
      case Some(product) => {
        Ok(views.html.product.detail(product))
      }
      case None => NotFound
    }
  }
}

