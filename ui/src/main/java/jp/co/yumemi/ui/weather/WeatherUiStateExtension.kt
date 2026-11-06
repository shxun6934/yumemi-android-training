package jp.co.yumemi.ui.weather

import jp.co.yumemi.model.weather.WeatherResponse
import kotlinx.serialization.InternalSerializationApi

object WeatherUiStateExtension {
    @OptIn(InternalSerializationApi::class)
    fun WeatherUiState.weather(): WeatherResponse? = when (this) {
        is WeatherUiState.Display -> weather
        is WeatherUiState.Loading -> weather
    }
}
