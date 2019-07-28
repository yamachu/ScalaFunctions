package com.example

import com.example.objects._

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
case class Functions(logger: utils.Logger) {
  def run(req: Requests): Either[Throwable, Response] = {
    logger.info("This is common Functions run")
    req.name match {
      case "yamachu" => Right(Response("Hi, admin! Thank you for using"))
      case _         => Left(new Exception(s"require admin user name, but ${req.name}"))
    }
  }

}
