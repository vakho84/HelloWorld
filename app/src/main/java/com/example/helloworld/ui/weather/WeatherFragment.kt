package com.example.helloworld.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.helloworld.databinding.FragmentWeatherBinding
import com.example.helloworld.retrofit.WeatherApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val KEY_TEMP_C = "KEY_TEMP_C"
private const val KEY_CITY = "KEY_CITY"
private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

class WeatherFragment : Fragment() {
    private val apiKey = "5924949e16a8492b9e8184723231212"

    private lateinit var weatherViewModel: WeatherViewModel

    private val weatherApi: WeatherApi

    private var _binding: FragmentWeatherBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com")
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime> {
                        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
                            val s = json!!.asString
                            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            return LocalDateTime.parse(s, dateTimeFormatter)
                        }

                    })
                    .create())).build()

        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        weatherViewModel = if (savedInstanceState == null) {
            WeatherViewModel(null, "Tbilisi", null)
        } else {
            WeatherViewModel(
                savedInstanceState.getFloat(KEY_TEMP_C),
                savedInstanceState.getString(KEY_CITY)!!,
                savedInstanceState.getString(KEY_IMAGE_URL)
            )
        }

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        updateUi()

        binding.weatherLoadWeatherButton.setOnClickListener {
            val city =  binding.weatherCityInput.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val weather = weatherApi.getWeather(apiKey, city)
                activity?.runOnUiThread {
                    weatherViewModel = WeatherViewModel(
                        weather.current.tempC,
                        city,
                        "https:" + weather.current.condition.icon
                    )
                    updateUi()
                }
            }
        }

        return binding.root
    }

    private fun updateUi() {
        binding.weatherTemperatureValue.text = weatherViewModel.tempC?.toString() ?: ""
        binding.weatherCityInput.setText(weatherViewModel.city)
        Glide.with(requireContext()).load(weatherViewModel.imageUrl).into(binding.weatherImage)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        weatherViewModel.tempC?.let { outState.putFloat(KEY_TEMP_C, it) }
        outState.putString(KEY_CITY, weatherViewModel.city)
        outState.putString(KEY_IMAGE_URL, weatherViewModel.imageUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

