package jp.co.yumemi.ui.weather

import androidx.compose.runtime.saveable.Saver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.use_case.weather.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetWeatherUseCase
): ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class)
    private var _uiState: MutableStateFlow<WeatherUiState> = savedStateHandle.saveable(
        key = "uiState",
        saver = Saver(
            save = { it.value },
            restore = { MutableStateFlow(it) }
        ),
        init = {
            MutableStateFlow(WeatherUiState.Loading)
        }
    )
    val uiState: StateFlow<WeatherUiState> get() = _uiState.asStateFlow()

    fun getWeather() {
        useCase.get(
            onSuccess = { weather ->
                _uiState.value = WeatherUiState.Display(weather = weather, showErrorDialog = false)
            },
            onFailure = { _ ->
                _uiState.value = (_uiState.value as? WeatherUiState.Display)?.copy(showErrorDialog = true)
                    ?: WeatherUiState.Display(weather = null, showErrorDialog = true)
            }
        )
    }

    fun dismissErrorDialog() {
        _uiState.value = (_uiState.value as? WeatherUiState.Display)?.copy(showErrorDialog = false) ?: _uiState.value
    }
}
