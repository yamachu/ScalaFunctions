package com.example.objects

import scala.scalajs.js.annotation._

@JSExportTopLevel("Requests")
case class Requests(name: String)

object Requests {
  def fromMap(obj: collection.mutable.Map[String, String]): Either[Throwable, Requests] =
    obj.get("name") match {
      case Some(name) => Right(Requests(name = name))
      case None       => Left(new Exception("name parameter is required"))
    }
}
