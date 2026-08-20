package jp.co.yumemi.repository.weather

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WeatherRepositoryTest {

    private val weather = mockk<YumemiWeather>()

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = WeatherRepository(weather)
    }

    @Test
    fun getSunny() = runTest {
        coEvery { weather.fetchWeatherAsync() } returns "sunny"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.SUNNY, value)
    }

    @Test
    fun getCloudy() = runTest {
        coEvery { weather.fetchWeatherAsync() } returns "cloudy"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.CLOUDY, value)
    }

    @Test
    fun getRainy() = runTest {
        coEvery { weather.fetchWeatherAsync() } returns "rainy"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.RAINY, value)
    }

    @Test
    fun getSnow() = runTest {
        coEvery { weather.fetchWeatherAsync() } returns "snow"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.SNOW, value)
    }

    @Test(expected = ApiError.UnknownException::class)
    fun getWeatherButThrowException() = runTest {
        coEvery { weather.fetchWeatherAsync() } throws Throwable()
        weatherRepository.getWeather()
    }
}
