package com.example.objects

import scala.annotation.meta.field
import scala.scalajs.js.annotation._

@JSExportTopLevel("Response")
case class Response(@(JSExport @field) result: String) extends JsonRender[Response]
