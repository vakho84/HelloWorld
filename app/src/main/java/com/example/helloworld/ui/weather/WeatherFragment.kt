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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.helloworld.BuildConfig


private const val KEY_TEMP_C = "KEY_TEMP_C"
private const val KEY_CITY = "KEY_CITY"
private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"
private const val KEY_REGION = "KEY_REGION"
private const val KEY_COUNTRY = "KEY_COUNTRY"
private const val KEY_WIND_SPEED_KPH = "KEY_WIND_SPEED_KPH"

class WeatherFragment : Fragment() {
   // private val apiKey = "5924949e16a8492b9e8184723231212"
      private val apiKey = BuildConfig.MY_API_KEY

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
            WeatherViewModel(
                null,
                "Tbilisi",
                null,
                null,
                null,
                null)
        } else {
            WeatherViewModel(
                savedInstanceState.getFloat(KEY_TEMP_C),
                savedInstanceState.getString(KEY_CITY)!!,
                savedInstanceState.getString(KEY_IMAGE_URL),
                savedInstanceState.getString(KEY_REGION),
                savedInstanceState.getString(KEY_COUNTRY),
                savedInstanceState.getFloat(KEY_WIND_SPEED_KPH)

            )
        }

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        val swipe: SwipeRefreshLayout = binding.swipeRefreshLayout

        updateUi()

       /*   binding.weatherLoadWeatherButton.setOnClickListener {
            val city =  binding.weatherCityInput.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val weather = weatherApi.getWeather(apiKey, city)
                activity?.runOnUiThread {
                    weatherViewModel = WeatherViewModel(
                        weather.current.tempC,
                        city,
                        "https:" + weather.current.condition.icon,
                        weather.location.region,
                        weather.location.country,
                        weather.current.windKph
                    )
                    updateUi()
                }
            }
        }  */

        swipe.setOnRefreshListener {

                // Update the data
                val city =  binding.weatherCityInput.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val weather = weatherApi.getWeather(apiKey, city)
                    activity?.runOnUiThread {
                        weatherViewModel = WeatherViewModel(
                            weather.current.tempC,
                            city,
                            "https:" + weather.current.condition.icon,
                            weather.location.region,
                            weather.location.country,
                            weather.current.windKph
                        )
                        updateUi()
                    }
                }

                // Hide swipe to refresh icon animation
                swipe.isRefreshing = false
        }

        return binding.root
    }

    private fun updateUi() {
        binding.weatherTemperatureValue.text = weatherViewModel.tempC?.toString() ?: ""
        binding.weatherCityInput.setText(weatherViewModel.city)
        Glide.with(requireContext()).load(weatherViewModel.imageUrl).into(binding.weatherImage)
        binding.weatherRegionValue.text = weatherViewModel.region?: ""
        binding.weatherCountryValue.text = weatherViewModel.country?: ""
        binding.weatherWindSpeedValue.text = weatherViewModel.windSpdKph?.toString() ?: ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        weatherViewModel.tempC?.let { outState.putFloat(KEY_TEMP_C, it) }
        outState.putString(KEY_CITY, weatherViewModel.city)
        outState.putString(KEY_IMAGE_URL, weatherViewModel.imageUrl)
        outState.putString(KEY_REGION, weatherViewModel.region)
        outState.putString(KEY_COUNTRY, weatherViewModel.country)
        weatherViewModel.windSpdKph?.let { outState.putFloat(KEY_WIND_SPEED_KPH, it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

