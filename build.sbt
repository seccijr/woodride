name := "woodride"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "sonatype-snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots/",
  "neo4j-releases"      at "http://m2.neo4j.org/content/repositories/releases",
  "anormcypher"         at "http://repo.anormcypher.org/",
  "Typesafe repository"   at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "org.anormcypher" %% "anormcypher" % "0.4.4",
  "com.softwaremill.macwire" %% "macros" % "0.5",
  "com.typesafe.play" %% "play" % "2.2.2",
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings

unmanagedSourceDirectories in Compile += baseDirectory.value / "abstract"
