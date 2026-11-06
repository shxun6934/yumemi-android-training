package jp.co.yumemi.repository.weather

import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.WeatherRequest
import jp.co.yumemi.model.weather.WeatherResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weather: YumemiWeather
) {

    suspend fun getWeather(request: WeatherRequest): WeatherResponse = try {
        val json = Json.encodeToString(request)
        weather.fetchJsonWeatherAsync(json).toModel()
    } catch (e: Throwable) {
        throw ApiError.UnknownException(e)
    }

    private fun String.toModel(): WeatherResponse {
        val json = Json {
            decodeEnumsCaseInsensitive = true
        }
        return json.decodeFromString<WeatherResponse>(this)
    }
}
