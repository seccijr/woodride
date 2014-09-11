package dtos

trait TProductDTO {
  val ref: String
  val sort: String
  val name: String
  val color: String
  val picture: String
  val onSales: Boolean
  val mainPrice: String
  def outOfStock: Boolean
}
