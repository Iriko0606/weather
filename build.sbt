lazy val akkaHttpVersion = "10.2.0"
lazy val akkaVersion    = "2.6.8"
lazy val circeVersion = "0.12.3"
lazy val awsVersion = "1.11.881"
lazy val softWareAwsVersion = "2.15.17"
lazy val scalaTest = "3.0.8"
lazy val logback = "1.2.3"
lazy val cats-core = "2.0.0"
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.13.3"
    )),
    name := "weather",
    libraryDependencies ++= Seq(
      //akka
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % logback,
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % scalaTest       % Test,
      //cats
      "org.typelevel"     %% "cats-core"                % cats-core,
      "io.circe"          %% "circe-core"               % circeVersion,
      "io.circe"          %% "circe-generic"            % circeVersion,
      "io.circe"          %% "circe-parser"             % circeVersion,
      "com.amazonaws" % "aws-java-sdk" % awsVersion,
      "com.amazonaws" % "aws-java-sdk-s3" % awsVersion
    )
  )