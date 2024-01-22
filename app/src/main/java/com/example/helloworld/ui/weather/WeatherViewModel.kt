package com.example.helloworld.ui.weather

data class WeatherViewModel(
    val tempC: Float?,
    val city: String,
    val imageUrl: String?,
    val region: String?,
    val country: String?,
    val windSpdKph: Float?
)
