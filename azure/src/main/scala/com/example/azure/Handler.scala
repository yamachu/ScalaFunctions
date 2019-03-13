package com.example.azure

import java.util._

import com.example._
import com.example.azure.utils.Logger
import com.example.objects.Requests
import com.microsoft.azure.functions._
import com.microsoft.azure.functions.annotation._

import collection.JavaConverters._

class Handler {
  @FunctionName("HttpHandlerGET")
  def runHttpGet(@HttpTrigger(
                   name = "req",
                   methods = Array(HttpMethod.GET),
                   authLevel = AuthorizationLevel.ANONYMOUS
                 ) request: HttpRequestMessage[Optional[String]],
                 context: ExecutionContext): HttpResponseMessage = {
    val logger = Logger(context.getLogger)
    logger.info("Scala HTTP GET trigger processed a request.")
    logger.info("HttpHandlerGET")

    // Parse query parameter
    val queryMap: collection.mutable.Map[String, String] =
      request.getQueryParameters.asScala

    (for {
      requests <- Requests.fromMap(queryMap)
      result   <- Functions(logger).run(requests)
    } yield result) match {
      case Right(s) =>
        request.createResponseBuilder(HttpStatus.OK).body(s).build
      case Left(e) =>
        request
          .createResponseBuilder(HttpStatus.BAD_REQUEST)
          .body(e.toString)
          .build
    }
  }

  @FunctionName("HttpHandlerPOST")
  def runHttpPost(@HttpTrigger(
                    name = "req",
                    methods = Array(HttpMethod.POST),
                    authLevel = AuthorizationLevel.ANONYMOUS
                  ) request: HttpRequestMessage[Requests],
                  context: ExecutionContext): HttpResponseMessage = {
    val logger = Logger(context.getLogger)
    logger.info("Scala HTTP POST trigger processed a request.")

    (for {
      result <- Functions(logger).run(request.getBody)
    } yield result) match {
      case Right(s) =>
        request.createResponseBuilder(HttpStatus.OK).body(s).build
      case Left(e) =>
        request
          .createResponseBuilder(HttpStatus.BAD_REQUEST)
          .body(e.toString)
          .build
    }
  }
}
