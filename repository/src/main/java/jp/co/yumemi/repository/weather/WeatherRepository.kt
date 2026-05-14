package jp.co.yumemi.repository.weather

import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weather: YumemiWeather
) {

    fun getWeather(): Weather = try {
        weather.fetchThrowsWeather().toModel()
    } catch (e: Throwable) {
        throw ApiError.UnknownException(e)
    }

    private fun String.toModel(): Weather = when (this) {
        "sunny" -> Weather.SUNNY
        "cloudy" -> Weather.CLOUDY
        "rainy" -> Weather.RAINY
        else -> Weather.SNOW
    }
}
