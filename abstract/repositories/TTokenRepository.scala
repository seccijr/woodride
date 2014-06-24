package repositories

import securesocial.core.providers.Token

trait TTokenRepositoryComposition {
  val tokenRepository: TTokenRepository
}

trait TTokenRepository {
  def getToken(token: String): Option[Token]
}
