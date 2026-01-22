package jp.co.yumemi.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import jp.co.yumemi.use_case.weather.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WeatherViewModel(
    private val useCase: GetWeatherUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<WeatherUiState> = MutableStateFlow(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun getWeather() {
        useCase.get(
            onSuccess = { weather ->
                _uiState.value = WeatherUiState.Display(weather = weather, showErrorDialog = false)
            },
            onFailure = { _ ->
                _uiState.update { currentUiState ->
                    (currentUiState as? WeatherUiState.Display)?.copy(showErrorDialog = true)
                        ?: WeatherUiState.Display(weather = null, showErrorDialog = true)
                }
            }
        )
    }

    fun dismissErrorDialog() {
        _uiState.update { currentUiState ->
            (currentUiState as? WeatherUiState.Display)?.copy(showErrorDialog = false)
                ?: currentUiState
        }
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
