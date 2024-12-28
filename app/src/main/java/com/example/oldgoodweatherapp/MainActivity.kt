package com.example.oldgoodweatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCity: EditText = findViewById(R.id.etCity)
        val btnFetchWeather: Button = findViewById(R.id.btnFetchWeather)
        val tvLocation: TextView = findViewById(R.id.tvLocation)
        val tvTemperature: TextView = findViewById(R.id.tvTemperature)
        val tvDescription: TextView = findViewById(R.id.tvDescription)
        val ivWeatherIcon: ImageView = findViewById(R.id.ivWeatherIcon)

        // Устанавливаем стандартный город "Москва" при старте
        fetchWeatherData("Москва", tvLocation, tvTemperature, tvDescription, ivWeatherIcon)

        // Обновляем данные погоды по нажатию на кнопку
        btnFetchWeather.setOnClickListener {
            val city = etCity.text.toString().trim()
            if (city.isNotEmpty()) {
                fetchWeatherData(city, tvLocation, tvTemperature, tvDescription, ivWeatherIcon)
            } else {
                tvLocation.text = "Название города"
            }
        }
    }

    private fun fetchWeatherData(
        location: String,
        tvLocation: TextView,
        tvTemperature: TextView,
        tvDescription: TextView,
        ivWeatherIcon: ImageView
    ) {
        RetrofitInstance.api.getCurrentWeather(location).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    tvLocation.text = weather?.name
                    tvTemperature.text = "${weather?.main?.temp}°C"
                    tvDescription.text = weather?.weather?.get(0)?.description
                    Picasso.get()
                        .load("https://openweathermap.org/img/wn/${weather?.weather?.get(0)?.icon}@2x.png")
                        .into(ivWeatherIcon)
                } else {
                    tvLocation.text = "Город не найден"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                tvLocation.text = "Ошибка: ${t.message}"
            }
        })
    }
}