package jp.co.yumemi.use_case.weather

import android.content.Context
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.repository.weather.WeatherRepository

class GetWeatherUseCase(context: Context) {

    private val repository: WeatherRepository = WeatherRepository(context)

    fun get(): Weather = repository.getWeather()
}
