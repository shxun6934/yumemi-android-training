package jp.co.yumemi.repository.weather

import io.mockk.coEvery
import io.mockk.mockk
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.model.weather.WeatherRequest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WeatherRepositoryTest {

    private val weather = mockk<YumemiWeather>()

    private lateinit var weatherRepository: WeatherRepository

    private val requestJson = """
        {"area":"東京","date":"2020-04-01T12:00"}
    """.trimIndent()

    private val request = WeatherRequest(
        area = "東京",
        date = "2020-04-01T12:00"
    )

    private fun response(weather: String): String = """
        {
          "weather": "$weather"
          "maxTemp": "20",
          "minTemp": "-20",
          "area": "東京",
          "date": "2020-04-01T12:00"
        }
    """.trimIndent()

    @Before
    fun setup() {
        weatherRepository = WeatherRepository(weather)
    }

    @Test
    fun getSunny() = runTest {
        coEvery { weather.fetchJsonWeatherAsync(requestJson) } returns response("sunny")
        val value = weatherRepository.getWeather(request)
        assertEquals(Weather.SUNNY, value.weather)
    }

    @Test
    fun getCloudy() = runTest {
        coEvery { weather.fetchJsonWeatherAsync(requestJson) } returns response("cloudy")
        val value = weatherRepository.getWeather(request)
        assertEquals(Weather.CLOUDY, value.weather)
    }

    @Test
    fun getRainy() = runTest {
        coEvery { weather.fetchJsonWeatherAsync(requestJson) } returns response("rainy")
        val value = weatherRepository.getWeather(request)
        assertEquals(Weather.RAINY, value.weather)
    }

    @Test
    fun getSnow() = runTest {
        coEvery { weather.fetchJsonWeatherAsync(requestJson) } returns response("snow")
        val value = weatherRepository.getWeather(request)
        assertEquals(Weather.SNOW, value.weather)
    }

    @Test(expected = ApiError.UnknownException::class)
    fun getWeatherButThrowException() = runTest {
        coEvery { weather.fetchJsonWeatherAsync(requestJson) } throws Throwable()
        weatherRepository.getWeather(request)
    }
}
