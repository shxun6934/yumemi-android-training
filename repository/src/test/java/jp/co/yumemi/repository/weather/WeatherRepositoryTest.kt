package jp.co.yumemi.repository.weather

import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
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
    fun getSunny() {
        every { weather.fetchThrowsWeather() } returns "sunny"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.SUNNY, value)
    }

    @Test
    fun getCloudy() {
        every { weather.fetchThrowsWeather() } returns "cloudy"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.CLOUDY, value)
    }

    @Test
    fun getRainy() {
        every { weather.fetchThrowsWeather() } returns "rainy"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.RAINY, value)
    }

    @Test
    fun getSnow() {
        every { weather.fetchThrowsWeather() } returns "snow"
        val value = weatherRepository.getWeather()
        assertEquals(Weather.SNOW, value)
    }

    @Test(expected = ApiError.UnknownException::class)
    fun getWeatherButThrowException() {
        every { weather.fetchThrowsWeather() } throws Throwable()
        weatherRepository.getWeather()
    }
}
