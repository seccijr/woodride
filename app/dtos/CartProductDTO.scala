package dtos

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class CartProductDTO(override val ref: String, override val quantity: Int) extends TCartProductDTO

object CartProductDTO {
  implicit val cartProductWrites = Writes[TCartProductDTO] { _ match {
    case cartProduct: CartProductDTO => Json.writes[CartProductDTO].writes(cartProduct)
  }}

  implicit val cartProductReads: Reads[TCartProductDTO] = (
    (JsPath \ "ref").read[String] and (JsPath \ "quantity").read[Int]
    )(CartProductDTO.apply _)

  implicit class CartProductDTOImprovements(val l: List[TCartProductDTO]) {
    def merge: List[TCartProductDTO] = {
      l.foldLeft(List[TCartProductDTO]()) { (z: List[TCartProductDTO], e: TCartProductDTO) =>
        z find (_.ref == e.ref) match {
          case Some(i) => CartProductDTO(i.ref, i.quantity + e.quantity) :: (z diff List(i))
          case _ => e :: z
        }
      }
    }
  }
}
