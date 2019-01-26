package com.example

import com.example.objects._

case class Functions(logger: utils.Logger) {
  def run(req: Requests): Either[Throwable, Response] = {
    logger.info("This is common Functions run")
    req.name match {
      case "foo" => Right(Response("bar"))
      case _     => Left(new Exception(s"require `foo`, but ${req.name}"))
    }
  }

}
