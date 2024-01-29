package com.example.helloworld.retrofit

import com.example.helloworld.data.ImageObject
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {
  //  @GET("/id/25/info")
  @GET("/id/{id}/info")
    suspend fun getOneImage(@Path("id") postId: Int): ImageObject
}