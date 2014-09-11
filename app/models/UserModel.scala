package models

import repositories.{TTokenRepositoryComposition, TUserRepositoryComposition}
import securesocial.core.providers.Token
import securesocial.core._

case class IdentityNames (
                           firstName: String,
                           lastName: String,
                           email: Option[String] = None,
                           avatarUrl: Option[String] = None
                           ) extends TIdentityNames


case class User (
                  override val identityId: IdentityId,
                  identityNames: TIdentityNames,
                  override val authMethod: AuthenticationMethod,
                  override val oAuth1Info: Option[OAuth1Info] = None,
                  override val oAuth2Info: Option[OAuth2Info] =  None,
                  override val passwordInfo: Option[PasswordInfo] = None
                  ) extends Identity {

  override val lastName: String = identityNames.lastName
  override val fullName: String = identityNames.lastName + ", " + identityNames.firstName
  override val avatarUrl: Option[String] = identityNames.avatarUrl
  override val email: Option[String] = identityNames.email
  override val firstName: String = identityNames.firstName
}

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
