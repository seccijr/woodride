package models

import securesocial.core.{IdentityId, Identity}
import securesocial.core.providers.Token

trait TUserModelComposition {
  val userModel: TUserModel
}

trait TIdentityNames {
  val firstName: String
  val lastName: String
  val email: Option[String]
  val avatarUrl: Option[String]
}

trait TUserModel {
  def getById(id: IdentityId): Option[Identity]
  def getByEmailAndProvider(email: String, providerId: String):Option[Identity]
  def getToken(toke: String): Option[Token]
}
