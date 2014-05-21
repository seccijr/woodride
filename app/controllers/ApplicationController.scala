package controllers

import play.api.mvc.Action

class ApplicationController extends TApplicationController {

  def index = Action {
    Ok(views.html.index("Inicio"))
  }

  def about = Action {
    Ok(views.html.page.about("Acerca"))
  }

  def blog = Action {
    Ok(views.html.blog.board("Blog"))
  }
}
