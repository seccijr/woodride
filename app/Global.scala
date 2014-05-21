import play.api.GlobalSettings
import com.softwaremill.macwire.MacwireMacros._

object Global extends GlobalSettings {
  private val injector = valsByClass(Injector)

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    injector(controllerClass).asInstanceOf[A]
  }
}