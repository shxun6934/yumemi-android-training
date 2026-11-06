package jp.co.yumemi.ui.weather

import jp.co.yumemi.model.weather.WeatherResponse
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
sealed class WeatherUiState {
    @Serializable
    data class Loading(val weather: WeatherResponse?) : WeatherUiState()

    @Serializable
    data class Display(val weather: WeatherResponse?, val showErrorDialog: Boolean) : WeatherUiState()
}
