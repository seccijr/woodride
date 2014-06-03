package repo

import models.{TLot, TProduct}

trait TLotRepositoryComposition {
  val lotRepository: TLotRepository
}

trait TLotRepository {
  def getByProduct(product: TProduct): List[TLot]
}
