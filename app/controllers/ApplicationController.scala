package controllers

import types.Product._
import play.api.mvc.Action
import play.api.Play
import services.TProductServiceComposition

class ApplicationController extends TApplicationController {
  self: TProductServiceComposition =>

  import dtos.ProductDTO._

  override def index = Action {
    val defaultPageSize = Play.current.configuration.getString("page.pageSize")
    val newArrivals = productService.getNewArrivals(1, defaultPageSize.get.toInt, SHIRT)
    Ok(views.html.index(newArrivals))
  }

  override def about = Action {
    Ok(views.html.page.about("Acerca"))
  }

  override def contact = Action {
    Ok(views.html.page.contact("Contacto"))
  }

  override def blog = Action {
    Ok(views.html.blog.board("Blog"))
  }
}
