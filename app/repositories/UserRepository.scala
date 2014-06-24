package repositories

import org.anormcypher.CypherResultRow
import securesocial.core._

class UserRepository extends TUserRepository {

  val userRowDef = s"""
       | user.userId, user.providerId, user.firstName, user.lastName, user.fullName,
       | user.email, user.avatarUrl, user.authMethod, oAuth1.token, oAuth1.secret,
       | oAuth2.accessToken, oAuth2.tokenType, oauth2.expiresIn, oAuth2.refreshToken,
       | password.hasher, password.password, password.salt
     """.stripMargin

  def userRowParser(row: CypherResultRow): Identity = {
    val identityId = IdentityId(row[String]("user.userId"), row[String]("user.providerId"))
    val authMethod = AuthenticationMethod(row[String]("user.authMethod"))
    val oAuth1 = OAuth1Info(row[String]("oAuth1.token"), row[String]("oAuth1.secret"))
    val oAuth2 = OAuth2Info(row[String]("oAuth2.accessToken"), row[Option[String]]("oAuth2.token"),
      row[Option[Int]]("oAuth2.expiresIn"), row[Option[String]]("oAuth2.refreshToken"))
    val passwordInfo = PasswordInfo(row[String]("password.hasher"), row[String]("password.password"),
      row[Option[String]]("password.salt"))

    SocialUser(identityId, row[String]("user.firstName"), row[String]("user.lastName"), row[String]("user.fullName"),
      row[Option[String]]("user.email"), row[Option[String]]("user.avatarUrl"), authMethod, Some(oAuth1), Some(oAuth2),
      Some(passwordInfo))
  }

  override def getById(id: String): Option[Identity] = {

  }

  override def getByEmailAndProvider(email: String, providerId: String): Option[Identity] = {

  }
}
