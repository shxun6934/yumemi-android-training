package jp.co.yumemi.repository.weather

import android.content.Context
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import kotlin.random.Random

class WeatherRepository(context: Context, random: Random = Random.Default) {

    private val weather: YumemiWeather = YumemiWeather(context, random)

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
