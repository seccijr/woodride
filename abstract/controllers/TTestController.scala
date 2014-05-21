package controllers

import play.api.mvc._

trait TTestController extends Controller {
  def product(ref: String): Action[AnyContent]
}

