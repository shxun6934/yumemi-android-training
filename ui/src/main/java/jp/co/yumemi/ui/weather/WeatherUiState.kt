package jp.co.yumemi.ui.weather

import jp.co.yumemi.model.weather.Weather

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Display(val weather: Weather?, val showErrorDialog: Boolean) : WeatherUiState
}
