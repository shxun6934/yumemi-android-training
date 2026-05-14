package jp.co.yumemi.ui.weather

import android.annotation.SuppressLint
import android.os.Parcelable
import jp.co.yumemi.model.weather.Weather
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
sealed interface WeatherUiState : Parcelable {
    data object Loading : WeatherUiState
    data class Display(val weather: Weather?, val showErrorDialog: Boolean) : WeatherUiState
}
