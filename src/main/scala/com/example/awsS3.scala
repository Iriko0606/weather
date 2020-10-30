package com.example

import java.io.File
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.{ObjectMetadata, PutObjectRequest, PutObjectResult}

object awsS3 {

  val region: String = "ap-northeast-1"
  val stringObjKeyName: String = "weather"
  val awsCredentials: BasicAWSCredentials = new BasicAWSCredentials("accesskey", "seacretkey")

  new BasicAWSCredentials("access_key_id", "secret_key_id")
  def putObjects (bucketName:String, fileName:String, fileObjKeyName: String): PutObjectResult = {

      val s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build()

      s3Client.putObject(bucketName, fileName,"Uploaded String Object")
      val request = new PutObjectRequest(bucketName,fileObjKeyName, new File(fileName))
      val metadata = new ObjectMetadata()
      metadata.setContentType("plain/text")
      metadata.addUserMetadata("title", "someTitle")
      request.setMetadata(metadata)
      s3Client.putObject(request)
  }

}