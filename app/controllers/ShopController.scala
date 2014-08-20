package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{Cookie, Action}
import dtos.{TCartProductDTO, CartProductDTO}
import dtos.CartProductDTO._

object ShopForms {
  val addToCart = Form(mapping(
    "ref" -> text,
    "quantity" -> number
  )(CartProductDTO.apply)(CartProductDTO.unapply))
}

class ShopController extends TShopController {
  def addToCart = Action { implicit request =>
    ShopForms.addToCart.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Bad format")
      },
      cartProduct => {
        val oldCart: List[TCartProductDTO] = request.cookies.get("cart") match {
          case Some(oldCartStr) => {
            Json.fromJson[List[TCartProductDTO]](Json.parse(oldCartStr.value)).get
          }
          case None => Nil
        }
        Ok("All right!").withCookies(Cookie("cart", Json.toJson(cartProduct :: oldCart merge).toString))
      }
    )
  }

  def cart = Action {
    Ok(views.html.shop.cart(""))
  }
}
