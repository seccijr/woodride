package controllers

import play.api.mvc.{AnyContent, Action, Controller}

trait TProductController extends Controller {
  def catalog(pageParam: Option[Int], pageSizeParam: Option[Int]): Action[AnyContent]
  def details(refParam: String): Action[AnyContent]
}
