package com.example.helloworld.retrofit

import com.example.helloworld.data.Image
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("/id/25/info")
    suspend fun getImage(): Image
}