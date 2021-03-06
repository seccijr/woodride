package repositories

import models.{User, IdentityNames}
import org.anormcypher.{NeoNode, Cypher, CypherResultRow}
import securesocial.core._
import play.api.libs.json._

class UserRepository extends TUserRepository {

  val userRowDef = s"""
     i.id, i.firstName, i.lastName, i.email, i.avatarUrl, p.method, p.id, ai
     """.stripMargin



  def userRowParser(row: CypherResultRow): Identity = {
    val identityId = IdentityId(row[String]("i.id"), row[String]("p.id"))
    val identityNames = IdentityNames(row[String]("i.firstName"), row[String]("i.lastName"),
      Some(row[String]("i.email")), Some(row[String]("i.avatarUrl")))
    val authInfo = row[NeoNode]("ai").props.asInstanceOf[Map[String, String]]
    val authMethod = AuthenticationMethod(row[String]("p.method"))
    authMethod match {
      case AuthenticationMethod("oauth1") => {
        val oAuth1 = OAuth1Info(authInfo.getOrElse("token", ""), authInfo.getOrElse("secret", ""))
        User(identityId, identityNames, authMethod, Some(oAuth1))
      }
      case AuthenticationMethod("oauth2") => {
        val oAuth2 = OAuth2Info(authInfo.getOrElse("token", ""), authInfo.get("tokenType"),
          authInfo.get("exiration").asInstanceOf[Option[Int]], authInfo.get("refresh"))
        User(identityId, identityNames, authMethod, None, Some(oAuth2))
      }
      case AuthenticationMethod("userpass") =>
        val passwordInfo = PasswordInfo(
          authInfo.getOrElse("hasher", ""),
          authInfo.getOrElse("password", ""),
          authInfo.get("salt")
        )
        User(identityId, identityNames, authMethod, None, None, Some(passwordInfo))
      case _ =>
        User(identityId, identityNames, authMethod)
    }

  }

  override def getById(id: String): Option[Identity] = {
    Cypher(
      s"""
        |MATCH (ai:AuthInfo)<-[air:AUTH_INFO]-(i:Identity{id: {id}})-[pr:AUTHENTICATED_BY]->(p:AuthProvider)
        |RETURN ${userRowDef}
      """.stripMargin)
      .on("id" -> id)()
      .headOption.map(userRowParser)
  }

  override def getByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    Cypher(
      s"""
        |MATCH (ai:AuthInfo)<-[:AUTH_INFO]-(i:Identity{email: {email}}),
        |(i)-[:AUTHENTICATED_BY]->(p:AuthProvider{id: {providerId}})
        |RETURN ${userRowDef}
      """.stripMargin)
      .on("email" -> email, "providerId" -> providerId)()
      .headOption.map(userRowParser)
  }
}
