package jp.co.yumemi.use_case.weather

import jp.co.yumemi.model.weather.WeatherRequest
import jp.co.yumemi.model.weather.WeatherResponse
import jp.co.yumemi.repository.weather.WeatherRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend fun get(
        area: String,
        onSuccess: (WeatherResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val request = WeatherRequest(
            area = area,
            date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        )
        runCatching {
            repository.getWeather(request)
        }.fold(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}
