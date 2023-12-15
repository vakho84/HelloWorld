package com.example.helloworld.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.helloworld.databinding.FragmentWeatherBinding
import com.example.helloworld.retrofit.WeatherApi
import com.example.helloworld.retrofit.WeatherResponseBody
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

class WeatherFragment : Fragment() {

   private val apiKey = "5924949e16a8492b9e8184723231212"
  // private var city = "Tbilisi"
    private lateinit var city: String
    private lateinit var weather: WeatherResponseBody
    private var isEmpty = false

    private var _binding: FragmentWeatherBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val button = binding.weatherLoadWeatherButton

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

        val weatherApi = retrofit.create(WeatherApi::class.java)
        var  customUrl: String?


        if ( isEmpty) {
            binding.weatherTemperatureValue.text = weather.current.tempC.toString()
            customUrl = "https:" + weather.current.condition.icon
            Glide.with(requireContext()).load(customUrl).into(binding.weatherImage)
        }

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (binding.weatherCityInput.text.toString() == "")
                {city = "Tbilisi"
                    binding.weatherCityInput.setText(city)
                }
                else city = binding.weatherCityInput.text.toString()
                 weather = weatherApi.getWeather(apiKey, city)
                isEmpty = true
                activity?.runOnUiThread {
                    binding.weatherTemperatureValue.text = weather.current.tempC.toString()
                    customUrl = "https:" + weather.current.condition.icon
                    Glide.with(requireContext()).load(customUrl).into(binding.weatherImage)
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

