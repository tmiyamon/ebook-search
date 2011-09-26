package com.tmiyamon

import unfiltered.request._
import unfiltered.response._
import unfiltered.scalate._
import unfiltered.netty._

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._

import org.clapper.avsl.Logger

import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.layout._

import java.util.Date
import java.io.File

case class EBook(title: String, author: String, publisher: String)

/** unfiltered plan */
class App extends unfiltered.filter.Plan {
  import QParams._

  val logger = Logger(classOf[App])

  val conn = MongoConnection("localhost", 27017)("ebook")
  val ebookColl = conn("ebook")
  val ebookGrater = grater[EBook]

  val templateDirs = List(new File("src/main/resources/templates"), new File("resources/templates"))
  val scalateMode = "production"
  implicit val engine = new TemplateEngine(templateDirs, scalateMode)
  engine.layoutStrategy = new DefaultLayoutStrategy(engine, "default.jade")

  def intent = {

    case req @ GET(Path("/search") & Params(p)) =>
        val params = Map[String, Seq[String]]()
        val query = params.mkString(", ")
        logger.debug("GET search: %s" format params)
        val results = ebookColl.find().map(ebookGrater.asObject(_)).toList
        logger.info(new File(".").getAbsolutePath())

        Ok ~> Scalate(req, "search.jade", ("query", query), ("books", results))

    case req @ GET(Path(Seg("simplesearch"::query::Nil))) =>
      Ok ~> Scalate(req, "simplesearch.jade", ("query", query))

    case req @ GET(Path("/")) =>
      Ok ~> Scalate(req, "index.jade")

  }
}
