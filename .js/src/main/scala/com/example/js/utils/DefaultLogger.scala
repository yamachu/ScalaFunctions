package com.example.js.utils

import com.example.utils.Logger

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("Logger")
case class JSLogger(infoImpl: js.Function1[String, Unit] = _ => {
  js.Dynamic.global.console.info(_)
  ()
}, warnImpl: js.Function1[String, Unit] = _ => {
  js.Dynamic.global.console.warn(_)
  ()
}) extends Logger {
  override def info(v: String): Unit = infoImpl(v)

  override def warn(v: String): Unit = warnImpl(v)
}
