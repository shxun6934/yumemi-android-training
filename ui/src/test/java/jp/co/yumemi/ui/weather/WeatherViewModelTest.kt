package jp.co.yumemi.ui.weather

import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.model.weather.WeatherResponse
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherViewModelTest {

    @OptIn(InternalSerializationApi::class)
    @Test
    fun testWeatherUiStateLoadingSerialization() {
        val state: WeatherUiState = WeatherUiState.Loading(weather = null)
        val json = Json.encodeToString(WeatherUiState.serializer(), state)
        val decoded = Json.decodeFromString(WeatherUiState.serializer(), json)
        assertEquals(state, decoded)
    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun testWeatherUiStateDisplaySerialization() {
        val weather = WeatherResponse(
            weather = Weather.SUNNY,
            maxTemp = 30,
            minTemp = 20,
            date = "2020-04-01T12:00",
            area = "東京"
        )
        val state: WeatherUiState = WeatherUiState.Display(weather = weather, showErrorDialog = true)
        val json = Json.encodeToString(WeatherUiState.serializer(), state)
        val decoded = Json.decodeFromString(WeatherUiState.serializer(), json)
        assertEquals(state, decoded)
    }
}
