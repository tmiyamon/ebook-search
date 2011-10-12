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

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

case class Book(title:String, url:String, author:String, publisher:String, providers:List[Provider])
case class Provider(name:String, url:String)

/** unfiltered plan */
class App extends unfiltered.filter.Plan {
  import QParams._

  val logger = Logger(classOf[App])

  val conn = MongoConnection("localhost", 27017)("ebook")
  val ebookColl = conn("ebook")
  val ebookGrater = grater[Book]

  // val templateDirs = List(new File("src/main/resources/templates"), new File("resources/templates"))
  // val scalateMode = "production"
  // implicit val engine = new TemplateEngine(templateDirs, scalateMode)
  // engine.layoutStrategy = new DefaultLayoutStrategy(engine, "default.jade")

  def intent = {

    case req @ GET(Path("/search") & Params(p)) =>
      // val params = Map[String, Seq[String]]()
      // val query = params.mkString(", ")
      // logger.debug("GET search: %s" format params)
      // val results = ebookColl.find().map(ebookGrater.asObject(_)).toList
      // logger.info(new File(".").getAbsolutePath())

      // Ok ~> Scalate(req, "search.jade", ("query", query), ("books", results))
      Ok ~> RichScalate(req, "search.jade")

    case req @ GET(Path(Seg("search"::"external"::id::Nil)) & Params(p)) =>
      Option(id) flatMap {
        case "ebookjapan" => Option(("id", "ebookjapan"))
        case _ => None

      } match {
        case Some(resp) =>
          Ok ~> Json(resp)
        case None =>
          NotFound
      }

    case req @ GET(Path("/")) =>
      Ok ~> RichScalate(req, "index.jade")

  }

}

object RichScalate {

  val templateDirs = List(
    new File("src/main/resources/templates"),
    new File("resources/templates"))

  val scalateMode = "production"
  val engine = new TemplateEngine(templateDirs, scalateMode)
  engine.layoutStrategy = new DefaultLayoutStrategy(engine, "default.jade")

  def apply[A, B](request: HttpRequest[A], template:String, attributes:(String,Any)*) = {
    Scalate(request, template, attributes:_*)(engine = engine)
  }
}

