package com.example.aws

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.example._
import com.example.aws.objects.AWSResponse
import com.example.aws.utils.Logger
import com.example.objects.Requests

class Handler extends RequestHandler[Requests, AWSResponse] {
  def handleRequest(request: Requests, context: Context): AWSResponse = {
    val logger = Logger.instance
    logger.info("Scala HTTP POST trigger processed a request.")

    (for {
      result <- Functions(logger).run(request)
    } yield result) match {
      case Right(s) => AWSResponse(s)
      case Left(e)  => throw e
    }
  }
}
