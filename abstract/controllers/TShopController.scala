package controllers

import play.api.mvc.{AnyContent, Action, Controller}

trait TShopController extends Controller {
  def addToCart: Action[AnyContent]
  def cart: Action[AnyContent]
}
