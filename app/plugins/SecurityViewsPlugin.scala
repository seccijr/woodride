package plugins

import play.Application
import play.api.data.Form
import play.api.mvc.{RequestHeader, Request}
import play.api.templates.{Txt, Html}
import securesocial.controllers.PasswordChange.ChangeInfo
import securesocial.controllers.Registration.RegistrationInfo
import securesocial.controllers.TemplatesPlugin
import securesocial.core.{SecuredRequest, Identity}

class SecurityViewsPlugin(application: Application) extends TemplatesPlugin {
  override def getLoginPage[A](implicit request: Request[A], form: Form[(String, String)], msg: Option[String]): Html = {
     views.html.security.login(form, msg)
  }

  override def getSignUpEmail(token: String)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getUnknownEmailNotice()(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getPasswordChangePage[A](implicit request: SecuredRequest[A], form: Form[ChangeInfo]): Html = ???

  override def getSendPasswordResetEmail(user: Identity, token: String)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getPasswordChangedNoticeEmail(user: Identity)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getWelcomeEmail(user: Identity)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getNotAuthorizedPage[A](implicit request: Request[A]): Html = ???

  override def getStartSignUpPage[A](implicit request: Request[A], form: Form[String]): Html = {
    securesocial.views.html.Registration.startSignUp(form)
  }

  override def getAlreadyRegisteredEmail(user: Identity)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = ???

  override def getSignUpPage[A](implicit request: Request[A], form: Form[RegistrationInfo], token: String): Html = ???

  override def getResetPasswordPage[A](implicit request: Request[A], form: Form[(String, String)], token: String): Html = ???

  override def getStartResetPasswordPage[A](implicit request: Request[A], form: Form[String]): Html = ???
}
