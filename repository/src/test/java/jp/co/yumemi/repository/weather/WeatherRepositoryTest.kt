package jp.co.yumemi.repository.weather

import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.model.weather.Weather
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class WeatherRepositoryTest {

    private val random = mockk<Random>()

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        weatherRepository = WeatherRepository(context, random)
    }

    @Test
    fun getSunny() {
        every { random.nextInt(any()) } returns Weather.SUNNY.ordinal
        val value = weatherRepository.getWeather()
        assertEquals(Weather.SUNNY, value)
    }

    @Test
    fun getCloudy() {
        every { random.nextInt(any()) } returns Weather.CLOUDY.ordinal
        val value = weatherRepository.getWeather()
        assertEquals(Weather.CLOUDY, value)
    }

    @Test
    fun getRainy() {
        every { random.nextInt(any()) } returns Weather.RAINY.ordinal
        val value = weatherRepository.getWeather()
        assertEquals(Weather.RAINY, value)
    }

    @Test
    fun getSnow() {
        every { random.nextInt(any()) } returns Weather.SNOW.ordinal
        val value = weatherRepository.getWeather()
        assertEquals(Weather.SNOW, value)
    }
}
