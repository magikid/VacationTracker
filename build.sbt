name := "playvacationtracker"

version := "1.0"

lazy val `playvacationtracker` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc , cache , javaWs , evolutions )

libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  