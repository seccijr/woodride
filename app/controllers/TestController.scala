package controllers

import play.api.mvc.Action
import services.TProductServiceComposition

class TestController extends TTestController {
  self: TProductServiceComposition =>
  def product(ref: String) = Action {
    val product = productService.getByRef(ref)
    Ok(views.html.product.detail(product))
  }
}

