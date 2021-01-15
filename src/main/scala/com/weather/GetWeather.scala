package com.weather

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import java.util.Date

object GetWeather{

  val cities: List[String] = List("Kawagoe","OMIYA")
  val key = "apikey"
  val date = "%tF" format new Date()
  implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext = system.executionContext

  def main(args: Array[String]): Unit = {
    for(city <- cities) {
      val content= Await.result(getWeather(s"https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key"), Duration.Inf)
      awsS3.putObjects("weatherinfo", s"weather_$city$date.json", content)
    }
  }
  def getWeather(url: String): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
    for {
      res <- responseFuture.map(r => r)
      bodyText <- Unmarshal(res.entity).to[String]
    } yield bodyText
  }
}