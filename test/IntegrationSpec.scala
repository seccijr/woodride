import org.junit.runner._
import org.specs2.runner._
import org.specs2.mutable.Specification
import org.openqa.selenium.phantomjs.PhantomJSDriverService

/**
 * An integration test will fire up a whole play application in a real (or headless) browser.
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "Application" should {

    "update the domain A type register IP" in new WithPhantomJS() {
      browser.goTo("http://www.1and1.es/login")
      browser.fill("#loginform-user").with("290502081")
      browser.fill("#loginform-password").with("estonoshararicos")
      browser.submit("#loginform-password")
      browser.$("li.navi-item item-domains a").click
      browser.$("li.sprite-ic-domainsselfserviceapp a").click
      browser.pageSource must contain("bla")
    }
  }

  private def getPhantomJSLanguageOption(langCode: String) =
    Map(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Accept-Language" -> langCode)

}