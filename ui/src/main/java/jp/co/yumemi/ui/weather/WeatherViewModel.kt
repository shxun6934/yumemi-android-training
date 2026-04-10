package jp.co.yumemi.ui.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.use_case.weather.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: GetWeatherUseCase
): ViewModel() {
    var uiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    fun getWeather() {
        useCase.get(
            onSuccess = { weather ->
                uiState = WeatherUiState.Display(weather = weather, showErrorDialog = false)
            },
            onFailure = { _ ->
                uiState = (uiState as? WeatherUiState.Display)?.copy(showErrorDialog = true)
                    ?: WeatherUiState.Display(weather = null, showErrorDialog = true)
            }
        )
    }

    fun dismissErrorDialog() {
        uiState = (uiState as? WeatherUiState.Display)?.copy(showErrorDialog = false) ?: uiState
    }
}
