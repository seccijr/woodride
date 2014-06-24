package services

import models.TUserModelComposition
import play.api.Application
import securesocial.core.{Identity, IdentityId, UserServicePlugin}
import securesocial.core.providers.Token

class UserService(application: Application) extends UserServicePlugin(application) {
  self: TUserModelComposition =>

  def find(id: IdentityId):Option[Identity] = {
    userModel.getById(id)
  }

  def findByEmailAndProvider(email: String, providerId: String):Option[Identity] = {
    userModel.getByEmailAndProvider(email, providerId)
  }

  def save(user: Identity) {
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