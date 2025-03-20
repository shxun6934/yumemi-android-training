package jp.co.yumemi.use_case.weather

import android.content.Context
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.repository.weather.WeatherRepository
import kotlin.random.Random

class GetWeatherUseCase(context: Context, random: Random = Random.Default) {

    private val repository: WeatherRepository = WeatherRepository(context, random)

    fun get(
        onSuccess: (Weather) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        runCatching {
            repository.getWeather()
        }.fold(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}
