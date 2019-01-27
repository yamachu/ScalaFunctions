package com.example.aws.objects
import com.example.objects.JsonRender

case class AWSResponse(body: String,
                       statusCode: Int = AWSResponse.defaultStatusCode,
                       headers: Map[String, String] = AWSResponse.defaultHeaders)

object AWSResponse {
  val defaultStatusCode = 200
  val defaultHeaders    = Map[String, String]("Content-Type" -> "application/json")

  def apply[A](body: JsonRender[A], statusCode: Int, headers: Map[String, String]): AWSResponse =
    new AWSResponse(body.toJson(), statusCode, headers)

  def apply[A](body: JsonRender[A], statusCode: Int): AWSResponse =
    new AWSResponse(body.toJson(), statusCode, defaultHeaders)

  def apply[A](body: JsonRender[A], headers: Map[String, String]): AWSResponse =
    new AWSResponse(body.toJson(), defaultStatusCode, headers)

  def apply[A](body: JsonRender[A]): AWSResponse =
    new AWSResponse(body.toJson(), defaultStatusCode, defaultHeaders)

}
