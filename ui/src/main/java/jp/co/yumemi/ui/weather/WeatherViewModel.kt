package jp.co.yumemi.ui.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import jp.co.yumemi.use_case.weather.GetWeatherUseCase

class WeatherViewModel(
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ?: throw IllegalStateException("Application not found in extras")

                return WeatherViewModel(
                    useCase = GetWeatherUseCase(context = application)
                ) as T
            }
        }
    }
}
