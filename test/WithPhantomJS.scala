import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.specs2.execute.AsResult
import org.specs2.execute.Result
import org.specs2.mutable.Around
import org.specs2.specification.Scope
import play.api.i18n.Lang
import play.api.test.Helpers._
import play.api.test.FakeApplication
import play.api.test.TestBrowser
import play.api.test.TestServer
import scala.collection.JavaConverters._

abstract class WithPhantomJS(val additionalOptions: Map[String, String] = Map()) extends Around with Scope {

  implicit def app = FakeApplication()

  implicit def port = play.api.test.Helpers.testServerPort

  lazy val browser: TestBrowser = {
    val defaultCapabilities = DesiredCapabilities.phantomjs
    val additionalCapabilities = new DesiredCapabilities(additionalOptions.asJava)
    val capabilities = new DesiredCapabilities(defaultCapabilities, additionalCapabilities)
    val driver = new PhantomJSDriver(capabilities)
    TestBrowser(driver, Some("http://localhost:" + port))
  }

  override def around[T: AsResult](body: => T): Result = {
    try {
      AsResult.effectively(body)
    } finally {
      browser.quit()
    }
  }
}