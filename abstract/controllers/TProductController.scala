package controllers

import play.api.mvc.{AnyContent, Action, Controller}

trait TProductController extends Controller {
  def catalog(page: Option[Int], pageSize: Option[Int]): Action[AnyContent]
  def details(ref: String): Action[AnyContent]
}
