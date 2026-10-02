package jp.co.yumemi.ui.weather

import jp.co.yumemi.model.weather.Weather

object WeatherUiStateExtension {
    fun WeatherUiState.weather(): Weather? = when (this) {
        is WeatherUiState.Display -> weather
        is WeatherUiState.Loading -> weather
    }
}
