name := "playvacationtracker"

version := "1.0"

lazy val `playvacationtracker` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc , cache , javaWs , evolutions , filters )
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"
libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
libraryDependencies +=   "com.feth" % "play-authenticate_2.11" % "0.7.2-SNAPSHOT"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += Resolver.sonatypeRepo("snapshots")

javacOptions ++= Seq("-Xlint:unchecked")
javacOptions ++= Seq("-Xlint:deprecation")