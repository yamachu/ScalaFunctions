package com.example

import com.example.objects._

case class Functions(logger: utils.Logger) {
  def run(req: Requests): Either[Throwable, String] = {
    logger.info("This is common Functions run")
    req.name match {
      case "foo" => Right("bar")
      case _     => Left(new Exception(s"require `foo`, but ${req.name}"))
    }
  }

}
