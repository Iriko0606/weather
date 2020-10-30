package com.weather

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.{PutObjectResult}

object awsS3 {

  val region: String = "ap-northeast-1"
  val stringObjKeyName: String = "weather"
  val awsCredentials: BasicAWSCredentials = new BasicAWSCredentials("accesskey", "seacretkey")

  def putObjects (bucketName:String, fileName: String, contents:String): PutObjectResult = {
    val s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(region)
      .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
      .build()

    s3Client.putObject(bucketName, fileName, contents)
  }

}