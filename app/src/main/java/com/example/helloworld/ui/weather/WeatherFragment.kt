package com.example.helloworld.ui.weather

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.helloworld.R
import com.example.helloworld.databinding.FragmentHomeBinding
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
import retrofit2.create
import java.io.BufferedInputStream
import java.lang.reflect.Type
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

class WeatherFragment : Fragment() {

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


        val apiKey = "5924949e16a8492b9e8184723231212"
       // var weatherType = "/current"
       var city = "Tbilisi"

        val tv = binding.weatherTemperatureValue
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
        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val weather = weatherApi.getWeather(apiKey, city)
                activity?.runOnUiThread {
                    tv.text = weather.current.tempC.toString()
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

