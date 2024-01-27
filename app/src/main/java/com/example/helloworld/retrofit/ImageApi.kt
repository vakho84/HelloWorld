package com.example.helloworld.retrofit

import com.example.helloworld.data.Image
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {
  //  @GET("/id/25/info")
  @GET("/id/{id}/info")
    suspend fun getImage(@Path("id") postId: Int): Image
}