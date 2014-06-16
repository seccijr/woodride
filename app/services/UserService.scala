package services

import play.api.Application
import securesocial.core.{Identity, IdentityId, UserServicePlugin}
import securesocial.core.providers.Token


class UserService(application: Application) extends UserServicePlugin(application) {
  def find(id: IdentityId):Option[Identity] = {
  }

  def findByEmailAndProvider(email: String, providerId: String):Option[Identity] = {
  }

  def save(user: Identity) {
  }

  def save(token: Token) = {
  }

  def findToken(token: String): Option[Token] = {
  }

  def deleteToken(uuid: String) {
  }

  def deleteExpiredTokens() {
  }
}