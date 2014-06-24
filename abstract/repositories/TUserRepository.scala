package repositories

import securesocial.core.{Identity, IdentityId}

trait TUserRepositoryComposition {
  val userRepository: TUserRepository
}

trait TUserRepository {
  def getById(id: String): Option[Identity]
  def getByEmailAndProvider(email: String, providerId: String):Option[Identity]
}
