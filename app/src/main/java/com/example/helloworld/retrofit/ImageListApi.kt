package com.example.helloworld.retrofit

import com.example.helloworld.data.ImageObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageListApi {
  //  https://picsum.photos/v2/list?page=2&limit=5
  @GET("/v2/list")
    suspend fun getImageList(@Query("page") page: String, @Query("limit") limit: String): ArrayList<ImageObject>
}