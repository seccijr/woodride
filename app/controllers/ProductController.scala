package controllers

import types.Product._
import play.api.mvc.Action
import play.api.Play
import services.TProductServiceComposition

class ProductController extends TProductController {
  self: TProductServiceComposition =>

  def catalog(page: Option[Int], pageSize: Option[Int]) = Action {
    val defaultPageSize = Play.current.configuration.getString("page.pageSize")
    val newArrivals = productService.getNewArrivals(page.getOrElse[Int](1), pageSize.getOrElse[Int](defaultPageSize.get.toInt), SHIRT)
    val bestSeller = productService.getBestSeller(page.getOrElse[Int](1), pageSize.getOrElse[Int](defaultPageSize.get.toInt), SHIRT)

    Ok(views.html.product.catalog(newArrivals, bestSeller))
  }

  def details(ref: String) = Action {
    val product = productService.getByRef(ref)
    Ok(views.html.product.detail(product))
  }
}

