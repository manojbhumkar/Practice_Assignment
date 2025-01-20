package com.example.weatherapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to weather details
        binding.btnFetchWeather.setOnClickListener {
            fetchWeather("London")
        }
    }

    private fun fetchWeather(city: String) {
        val apiKey = "19f5fb4a974d05a554154ca0463c9d8e"
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.weatherApi.getWeather(city, apiKey)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    displayWeather(weatherResponse)
                } else {
                    showError()
                }
            }
        }
    }
    private fun displayWeather(weatherResponse: WeatherResponse?) {
        if (weatherResponse != null) {
            binding.tvWeather.text = "Temp: ${weatherResponse.main.temp}°C, ${weatherResponse.weather[0].description}"
        }
    }

    private fun showError() {
        binding.tvWeather.text = "Error fetching weather data"
    }
}
