package controllers

import types.Product._
import play.api.mvc.Action
import play.api.Play
import services.TProductServiceComposition

class ApplicationController extends TApplicationController {
  self: TProductServiceComposition =>

  def index = Action {
    val defaultPageSize = Play.current.configuration.getString("page.pageSize")
    val newArrivals = productService.getNewArrivals(1, defaultPageSize.get.toInt, SHIRT)
    Ok(views.html.index(newArrivals))
  }

  def about = Action {
    Ok(views.html.page.about("Acerca"))
  }

  def contact = Action {
    Ok(views.html.page.contact("Contacto"))
  }

  def blog = Action {
    Ok(views.html.blog.board("Blog"))
  }
}
