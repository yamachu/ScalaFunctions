package com.example.objects

import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write
import org.json4s.NoTypeHints

trait JsonRender[A] { self: A =>
  implicit val formats = Serialization.formats(NoTypeHints)
  def toJson(): String = write(self)
}
