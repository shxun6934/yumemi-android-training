package jp.co.yumemi.use_case.weather

import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.every
import io.mockk.mockk
import jp.co.yumemi.model.error.ApiError
import jp.co.yumemi.model.weather.Weather
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
class GetWeatherUseCaseTest {

    private val random = mockk<Random>()

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        getWeatherUseCase = GetWeatherUseCase(context, random)
    }

    @Test
    fun successGetWeather() {
        every { random.nextInt(any(), any()) } returns 0
        every { random.nextInt(any()) } returns Weather.SUNNY.ordinal
        getWeatherUseCase.get(
            onSuccess = { value ->
                assertEquals(Weather.SUNNY, value)
            },
            onFailure = {}
        )
    }

    @Test
    fun failedGetWeather() {
        every { random.nextInt(any(), any()) } returns 4
        getWeatherUseCase.get(
            onSuccess = {},
            onFailure = {
                assertEquals(ApiError.UnknownException::class, it::class)
            }
        )
    }
}
