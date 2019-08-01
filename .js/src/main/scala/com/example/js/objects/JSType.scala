package com.example.js.objects

import scala.annotation.meta.field
import scala.scalajs.js.annotation._

trait JSEither[E, T] {
  val ok: Boolean
}

case class JSRight[E, T](@(JSExport @field) value: T) extends JSEither[E, T] {
  @(JSExport @field)
  val ok = true
}

case class JSLeft[E, T](@(JSExport @field) error: E) extends JSEither[E, T] {
  @(JSExport @field)
  val ok = false
}
