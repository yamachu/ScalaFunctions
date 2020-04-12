package com.example.js.utils

import com.example.utils.Logger

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("Logger")
case class JSLogger(infoImpl: js.Function1[String, Unit] = v => {
  js.Dynamic.global.console.info(v)
  ()
}, warnImpl: js.Function1[String, Unit] = v => {
  js.Dynamic.global.console.warn(v)
  ()
}) extends Logger {
  override def info(v: String): Unit = infoImpl(v)

  override def warn(v: String): Unit = warnImpl(v)
}
