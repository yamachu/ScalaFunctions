package com.example.aws

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.example._
import com.example.aws.utils.Logger
import com.example.objects.{Requests, Response}

class Handler extends RequestHandler[Requests, Response] {
  def handleRequest(request: Requests, context: Context): Response = {
    val logger = Logger.instance
    logger.info("Scala HTTP POST trigger processed a request.")

    (for {
      result <- Functions(logger).run(request)
    } yield result) match {
      case Right(s) => s
      case Left(e)  => throw e
    }
  }
}
