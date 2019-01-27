package com.example.aws.utils

import com.example.utils.{Logger => BaseLogger}

case class Logger() extends BaseLogger {
  def info(v: String): Unit = System.out.println(v)
  def warn(v: String): Unit = System.err.println(v)
}

object Logger {
  lazy val instance = Logger()
}