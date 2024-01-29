package com.example.helloworld.data

import java.time.LocalDateTime


data class WeatherLocation(
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class WeatherCondition(
    val text: String,
    val icon: String,
    val code: Int
)

data class CurrentWeather(
    val lastUpdatedEpoch: Long,
    val lastUpdated: LocalDateTime,
    val tempC: Float,
    val tempF: Float,
    val isDay: Int,

    val condition: WeatherCondition,

    val windMph: Float,
    val windKph: Float,
    val windDegree: Int,
    val windDir: String,
    val pressureMb: Float,
    val pressureIn: Float,
    val precipMm: Float,
    val precipIn: Float,
    val humidity: Int,
    val cloud: Int,
    val feelslikeC: Float,
    val feelslikeF: Float,
    val visKm: Float,
    val visMiles: Float,
    val uv: Float,
    val gustMph: Float,
    val gustKph: Float
)

data class WeatherResponseBody (
    val current: CurrentWeather,
    val location: WeatherLocation
)
