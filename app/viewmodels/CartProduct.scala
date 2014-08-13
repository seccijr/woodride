package viewmodels

/**
 * Created by secci on 8/13/14.
 */
class CartProduct(val ref: String, val quantity: Int) extends TCartProduct

object CartProduct {
  def apply(ref: String, quantity: Int): TCartProduct = new CartProduct(ref, quantity)

  def unapply(cartProduct: TCartProduct): Option[(String, Int)] = Some((cartProduct.ref, cartProduct.quantity))
}
