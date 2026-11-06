package jp.co.yumemi.use_case.weather

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.model.weather.WeatherRequest
import jp.co.yumemi.model.weather.WeatherResponse
import jp.co.yumemi.repository.weather.WeatherRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GetWeatherUseCaseTest {

    private val repository = mockk<WeatherRepository>()

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    private val request = WeatherRequest(
        area = "東京",
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
    )

    @Before
    fun setup() {
        getWeatherUseCase = GetWeatherUseCase(repository)
    }

    @Test
    fun successGetWeather() = runTest {
        coEvery { repository.getWeather(request) } returns WeatherResponse(
            weather = Weather.SUNNY,
            maxTemp = 20,
            minTemp = -20,
            date = "2020-04-01T12:00",
            area = "東京"
        )
        getWeatherUseCase.get(
            area = "東京",
            onSuccess = { value ->
                assertEquals(Weather.SUNNY, value.weather)
                assertEquals(20, value.maxTemp)
                assertEquals(-20, value.minTemp)
                assertEquals("2020-04-01T12:00", value.date)
                assertEquals("東京", value.area)
            },
            onFailure = {}
        )
    }

    @Test
    fun failedGetWeather() = runTest {
        coEvery { repository.getWeather(request) } throws ApiError.UnknownException(Throwable())
        getWeatherUseCase.get(
            area = "東京",
            onSuccess = {},
            onFailure = {
                assertEquals(ApiError.UnknownException::class, it::class)
            }
        )
    }
}
