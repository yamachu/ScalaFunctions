package com.example.objects

case class Requests(name: String)

object Requests {
  def fromMap(obj: collection.mutable.Map[String, String]): Either[Throwable, Requests] =
    obj.get("name") match {
      case Some(name) => Right(Requests(name = name))
      case None       => Left(new Exception("name parameter is required"))
    }
}
