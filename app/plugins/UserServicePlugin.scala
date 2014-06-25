package plugins

import models.{UserModel, TUserModelComposition}
import play.api.{Application, Logger, Plugin}
import play.api.libs.concurrent.Akka
import akka.actor.Cancellable
import repositories.{TokenRepository, TTokenRepositoryComposition, UserRepository, TUserRepositoryComposition}
import securesocial.core.{UserService => SercureSocialUserService}
import securesocial.core.providers.UsernamePasswordProvider
import services.UserService

class UserServicePlugin(application: Application) extends Plugin {
  val DefaultInterval = 5
  val DeleteIntervalKey = "securesocial.userpass.tokenDeleteInterval"

  var cancellable: Option[Cancellable] = None

  override def onStop() {
    cancellable.map( _.cancel() )
  }

  override def onStart() {
    import play.api.Play.current
    import scala.concurrent.duration._
    import play.api.libs.concurrent.Execution.Implicits._

    val i = application.configuration.getInt(DeleteIntervalKey).getOrElse(DefaultInterval)
    val userService = new UserService with TUserModelComposition {
      val userModel = new UserModel with TUserRepositoryComposition with TTokenRepositoryComposition {
        val userRepository = new UserRepository()
        val tokenRepository = new TokenRepository()
      }
    }

    cancellable = if ( UsernamePasswordProvider.enableTokenJob ) {
      Some(
        Akka.system.scheduler.schedule(0.seconds, i.minutes) {
          if ( Logger.isDebugEnabled ) {
            Logger.debug("[securesocial] calling deleteExpiredTokens()")
          }
          userService.deleteExpiredTokens()
        }
      )
    } else None

    SercureSocialUserService.setService(userService)
    Logger.info("[securesocial] loaded user service: %s".format(userService.getClass))
  }
}
