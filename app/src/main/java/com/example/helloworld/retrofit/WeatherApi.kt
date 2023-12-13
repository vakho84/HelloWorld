package com.example.helloworld.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/current.json?aqi=no")
    suspend fun getWeather(@Query ("key") apiKey: String, @Query("q") city: String): WeatherResponseBody
}