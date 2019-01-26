package com.example.utils

trait Logger {
  def info(v: String): Unit
  def warn(v: String): Unit
}
