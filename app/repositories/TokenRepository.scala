package repositories

import securesocial.core.providers.Token

class TokenRepository extends TTokenRepository {
  override def getToken(token: String): Option[Token] = {

  }
}
