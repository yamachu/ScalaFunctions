package com.example.scalajs

import com.example.Functions
import com.example.js.utils.JSLogger
import com.example.objects.{Requests, Response}
import com.example.utils.Logger

import scala.scalajs.js.annotation._

@JSExportTopLevel("Handler")
case class Handler(logger: Logger = JSLogger()) {
  @JSExport
  def run(req: Requests): Either[Throwable, Response] =
    Functions(logger).run(req)

}
