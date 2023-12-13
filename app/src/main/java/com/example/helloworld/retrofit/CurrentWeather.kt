package com.example.helloworld.retrofit

import android.service.notification.Condition
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

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
    val current: CurrentWeather
)
