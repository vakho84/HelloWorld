package com.example.helloworld.data.retrofit

import com.example.helloworld.data.ImageObjectWeb
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageListApi {
  //  https://picsum.photos/v2/list?page=2&limit=5
  @GET("/v2/list")
  suspend fun getImageList(@Query("page") page: String, @Query("limit") limit: String): ArrayList<ImageObjectWeb>

  @GET("/id/{id}/info")
  suspend fun getOneImage(@Path("id") postId: Int): ImageObjectWeb
}