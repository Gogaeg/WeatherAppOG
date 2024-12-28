import com.example.oldgoodweatherapp.BuildConfig
import com.example.oldgoodweatherapp.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("weather")

    fun getCurrentWeather(

        @Query("q") location: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): Call<WeatherResponse>
}