organization := "com.tmiyamon"

name := "ebook-search"

version := "0.1"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered" % "0.5.0",
   "net.databinder" %% "unfiltered-filter" % "0.5.0",
   "net.databinder" %% "unfiltered-netty" % "0.5.0",
   "net.databinder" %% "unfiltered-websockets" % "0.5.0",
   "net.databinder" %% "unfiltered-jetty" % "0.5.0",
   "net.databinder" %% "unfiltered-jetty-ajp" % "0.5.0",
   "net.databinder" %% "unfiltered-netty-server" % "0.5.0",
   "net.databinder" %% "unfiltered-uploads" % "0.5.0",
   "net.databinder" %% "unfiltered-utils" % "0.5.0",
   "net.databinder" %% "unfiltered-spec" % "0.5.0",
   "net.databinder" %% "unfiltered-scalatest" % "0.5.0",
   "net.databinder" %% "unfiltered-json" % "0.5.0",
   "net.databinder" %% "unfiltered-scalate" % "0.5.0",
   "net.databinder" %% "unfiltered-oauth" % "0.5.0",
   "javax.servlet" % "servlet-api" % "2.3" % "provided",
   "org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "jetty",
   "org.clapper" %% "avsl" % "0.3.6",
   "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT"
)

resolvers ++= Seq(
    "repo.novus rels" at "http://repo.novus.com/releases/",
    "repo.novus snaps" at "http://repo.novus.com/snapshots/"
)

seq(webSettings :_*)

unmanagedClasspath in Runtime <+= (scalaInstance) map { (scala) => Attributed.blank(scala.compilerJar) }

scalacOptions ++= Seq("-deprecation")
