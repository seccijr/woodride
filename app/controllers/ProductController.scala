package controllers

import types.Product._
import play.api.mvc.Action
import play.api.Play
import services.TProductServiceComposition

class ProductController extends TProductController {
  self: TProductServiceComposition =>

  def catalog(pageParam: Option[Int], pageSizeParam: Option[Int]) = Action {
    val defaultPageSize = Play.current.configuration.getString("page.pageSize")
    val page = pageParam.getOrElse[Int](1)
    val pageSize = pageSizeParam.getOrElse[Int](defaultPageSize.get.toInt)
    val newArrivals = productService.getNewArrivals(page, pageSize, SHIRT)
    val bestSeller = productService.getBestSeller(page, pageSize, SHIRT)

    Ok(views.html.product.catalog(newArrivals, bestSeller))
  }

  def details(refParam: String) = Action {
    val product = productService.getByRef(refParam)
    Ok(views.html.product.detail(product))
  }
}

