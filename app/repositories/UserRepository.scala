package repositories

import models.IdentityNames
import org.anormcypher.{Cypher, CypherResultRow}
import securesocial.core._

class UserRepository extends TUserRepository {

  val userRowDef = s"""
     i.id, provider.id, i.firstName, i.lastName,
     i.email, i.avatarUrl, provider.method,
     """.stripMargin

  def userRowParser(row: CypherResultRow): Identity = {
    val identityId = IdentityId(row[String]("i.id"), row[String]("provider.id"))
    val identityNames = IdentityNames(row[String]("user.firstName"), row[String]("user.lastName"),
      row[String]("user.fullName"), row[Option[String]]("user.email"), row[Option[String]]("user.avatarUrl"))
    val authMethod = AuthenticationMethod(row[String]("provider.method"))
    authMethod match {
      case AuthenticationMethod("oauth1") =>
        SocialUser(identityId, identit, authMethod, Some(oAuth1), Some(oAuth2),
          Some(passwordInfo))
      case AuthenticationMethod("oauth2") =>
      case AuthenticationMethod("usernamepassword") =>
      case _ =>
    }
    val oAuth1 = OAuth1Info(row[String]("oAuth1.token"), row[String]("oAuth1.secret"))
    val oAuth2 = OAuth2Info(row[String]("oAuth2.accessToken"), row[Option[String]]("oAuth2.token"),
      row[Option[Int]]("oAuth2.expiresIn"), row[Option[String]]("oAuth2.refreshToken"))
    val passwordInfo = PasswordInfo(row[String]("password.hasher"), row[String]("password.password"),
      row[Option[String]]("password.salt"))

  }

  override def getById(id: String): Option[Identity] = {
    Cypher(
      s"""
        |MATCH (authMethod:AuthMethod)<-[r2:AUTH_METHOD]-(i:Identity)-[r1:AUTHENTICATED_BY]->(provider:AuthProvider),
        |(i)-[r3:PASSWORD]->(p:Password)
        |RETURN ${userRowDef}
      """.stripMargin)
      .on("id" -> id)()
      .headOption.map(userRowParser)
  }

  override def getByEmailAndProvider(email: String, providerId: String): Option[Identity] = {

  }
}
