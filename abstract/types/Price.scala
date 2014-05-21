package types

object PriceRel extends Enumeration {
  type PriceRelType = Value
  val MAIN_PRICE = Value("MAIN_PRICE")
  val COST_PRICE = Value("COST_PRICE")
}

