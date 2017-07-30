name := "playvacationtracker"

version := "1.0"

lazy val `playvacationtracker` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc , cache , javaWs , evolutions , filters )

libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"

libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"

libraryDependencies += "ws.securesocial" %% "securesocial" % "2.1.4.1-for-play24"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/"