package repositories

import org.anormcypher.{TypeDoesNotMatch, Column, CypherResultRow, Cypher}
import org.joda.time.DateTime
import securesocial.core.providers.Token

class TokenRepository extends TTokenRepository {

  implicit def rowToDateTime = Column.nonNull[DateTime] {
    (value, meta) => value match {
      case i: Long => Right(new DateTime(i))
      case x => Left(TypeDoesNotMatch(s"Cannot convert $x: ${x.getClass} to String for column ${meta.column}"))
    }
  }

  val tokenRowDef = s"""
      t.uuid, t.email, t.date, t.expiration, t.isSignUp
     """.stripMargin

  def tokenRowParser(row: CypherResultRow): Token = {
    Token(row[String]("t.uuid"), row[String]("t.email"), row[DateTime]("t.date"), row[DateTime]("t.expiration"),
      row[Boolean]("t.isSignUp"))
  }

  override def getToken(token: String): Option[Token] = {
    Cypher(
      s"""
        |MATCH (t:SessionToken{uuid: {token}}),
        |RETURN ${tokenRowDef}
      """.stripMargin)
      .on("token" -> token)()
      .headOption.map(tokenRowParser)
  }
}
