package com.example.aws

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.amazonaws.services.lambda.runtime.events.{
  APIGatewayProxyRequestEvent,
  APIGatewayProxyResponseEvent
}
import com.example.objects.Requests
import com.example.Functions
import com.example.aws.utils.Logger
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.collection.JavaConverters._

class Handler extends RequestHandler[APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent] {
  def handleRequest(input: APIGatewayProxyRequestEvent,
                    context: Context): APIGatewayProxyResponseEvent = {
    val logger = Logger.instance

    logger.info("Scala HTTP POST trigger processed a request.")

    (for {
      json   <- decode[Requests](input.getBody)
      result <- Functions(logger).run(json)
    } yield result) match {
      case Right(s) =>
        new APIGatewayProxyResponseEvent()
          .withStatusCode(200)
          .withHeaders(
            Map(
              "Content-Type" -> "application/json",
            ).asJava
          )
          .withBody(s.asJson.toString())
      case Left(e) =>
        new APIGatewayProxyResponseEvent()
          .withStatusCode(500)
          .withHeaders(
            Map(
              "Content-Type" -> "plain/text",
            ).asJava
          )
          .withBody(e.toString)
    }
  }
}
