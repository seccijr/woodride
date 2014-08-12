package services

import models.TUserModelComposition
import securesocial.core.{Identity, IdentityId, UserService => SecureSocialUserService}
import securesocial.core.providers.Token

class UserService extends SecureSocialUserService {
  self: TUserModelComposition =>

  def find(id: IdentityId):Option[Identity] = {
    userModel.getById(id)
  }

  def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    userModel.getByEmailAndProvider(email, providerId)
  }

  def save(user: Identity): Identity = {
    user
  }

  def save(token: Token) = {
  }

  def findToken(token: String): Option[Token] = {
    userModel.getToken(token)
  }

  def deleteToken(uuid: String) {
  }

  def deleteExpiredTokens() {
  }
}