package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import java.io.{PrintWriter}
import java.util.Date

object GetWeather{

  val city = "Kawagoe"
  val key = "apikey"
  val url = s"https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${key}"
  val date = "%tF" format new Date()

  implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext = system.executionContext
  def main(args: Array[String]): Unit = {
    val weather = Await.result(getWeather(url), Duration.Inf)

    writeJsonFile(weather)
    awsS3.putObjects("weatherinfo", s"weather${date}.json", "weather")
  }
  def getWeather(url: String): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
    for {
      res <- responseFuture.map(r => r)
      bodyText <- Unmarshal(res.entity).to[String]
    } yield bodyText
  }

  def writeJsonFile(contents: String): Unit = {
    val file = new PrintWriter(s"weather${date}.json")
    file.write(contents)
    file.close()
  }

}