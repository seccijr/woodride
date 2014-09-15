import org.junit.runner._
import org.specs2.runner._
import play.api.i18n._
import org.specs2.mutable.Specification
import org.openqa.selenium.phantomjs.PhantomJSDriverService

/**
 * An integration test will fire up a whole play application in a real (or headless) browser.
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  val enUSLangCode = "en-US"
  val ptBRLangCode = "pt-BR"

  val enUSOptions = getPhantomJSLanguageOption(enUSLangCode)
  val ptBROptions = getPhantomJSLanguageOption(ptBRLangCode)

  "Application" should {

    "work from within a browser with en-US language" in new WithPhantomJS(enUSOptions) {
      browser.goTo("http://localhost:" + port)
      implicit val lang = Lang(enUSLangCode)
      val expected = Messages("home.index.featured_lead")
      browser.pageSource must contain(expected)
    }

    "work from within a browser with pt-BR language" in new WithPhantomJS(ptBROptions) {
      browser.goTo("http://localhost:" + port)
      implicit val lang = Lang(ptBRLangCode)
      val expected = Messages("home.index.featured_lead")
      browser.pageSource must contain(expected)
    }

  }

  private def getPhantomJSLanguageOption(langCode: String) =
    Map(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Accept-Language" -> langCode)

}