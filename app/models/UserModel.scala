package models

import repositories.{TTokenRepositoryComposition, TTokenRepository, TUserRepositoryComposition}
import securesocial.core.providers.Token
import securesocial.core.{IdentityId, Identity}

class UserModel extends TUserModel {
  self: TUserRepositoryComposition with TTokenRepositoryComposition =>

  override def getById(id: IdentityId): Option[Identity] = {
    userRepository.getById(id.userId)
  }

  override def getByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    userRepository.getByEmailAndProvider(email, providerId)
  }

  override def getToken(token: String): Option[Token] = {
    tokenRepository.getToken(token)
  }
}
