package models

import securesocial.core.{IdentityId, Identity}
import securesocial.core.providers.Token

trait TUserModelComposition {
  val userModel: TUserModel
}

trait TUserModel {
  def getById(id: IdentityId): Option[Identity]
  def getByEmailAndProvider(email: String, providerId: String):Option[Identity]
  def getToken(toke: String): Option[Token]
}
