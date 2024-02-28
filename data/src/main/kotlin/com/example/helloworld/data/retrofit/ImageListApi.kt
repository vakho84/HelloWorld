package com.example.helloworld.data.retrofit

import com.example.helloworld.data.ImageObjectWeb
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ImageListApi {
  //  https://picsum.photos/v2/list?page=2&limit=5
  @GET("/v2/list")
  suspend fun getImageList(@Query("page") page: Int, @Query("limit") limit: Int): ArrayList<ImageObjectWeb>

  @GET("/id/{id}/info")
  suspend fun getOneImage(@Path("id") postId: Int): ImageObjectWeb
}