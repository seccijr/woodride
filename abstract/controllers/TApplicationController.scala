package controllers

import play.api.mvc._

trait TApplicationController extends Controller {
  def index: Action[AnyContent]
  def about: Action[AnyContent]
  def contact: Action[AnyContent]
  def blog: Action[AnyContent]
}
