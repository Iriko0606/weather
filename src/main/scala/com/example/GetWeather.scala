package com.example
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import cats._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
object GetWeather {
  val city = "Kawagoe"
  val key = "83d025aeaf06051689e0a2a8a892b576"
  val url = s"https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${key}"
  implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext = system.executionContext
  def main(args: Array[String]): Unit = {
    val weather = Await.result(getWeather(url), Duration.Inf)
    println(weather)
  }
  def getWeather(url: String): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
    for {
      res <- responseFuture.map(r => r)
      bodyText <- Unmarshal(res.entity).to[String]
    } yield bodyText
  }
}