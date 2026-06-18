package jp.co.yumemi.use_case.weather

import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.repository.weather.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
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
