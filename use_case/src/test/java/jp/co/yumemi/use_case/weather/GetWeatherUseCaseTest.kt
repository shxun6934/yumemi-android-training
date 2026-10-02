package jp.co.yumemi.use_case.weather

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.repository.weather.WeatherRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetWeatherUseCaseTest {

    private val repository = mockk<WeatherRepository>()

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        getWeatherUseCase = GetWeatherUseCase(repository)
    }

    @Test
    fun successGetWeather() = runTest {
        coEvery { repository.getWeather() } returns Weather.SUNNY
        getWeatherUseCase.get(
            onSuccess = { value ->
                assertEquals(Weather.SUNNY, value)
            },
            onFailure = {}
        )
    }

    @Test
    fun failedGetWeather() = runTest {
        coEvery { repository.getWeather() } throws ApiError.UnknownException(Throwable())
        getWeatherUseCase.get(
            onSuccess = {},
            onFailure = {
                assertEquals(ApiError.UnknownException::class, it::class)
            }
        )
    }
}
