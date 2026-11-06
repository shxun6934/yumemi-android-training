package jp.co.yumemi.ui.weather

import androidx.compose.runtime.saveable.Saver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.use_case.weather.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetWeatherUseCase
): ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class, InternalSerializationApi::class)
    private val _uiState: MutableStateFlow<WeatherUiState> = savedStateHandle.saveable(
        key = "uiState",
        saver = Saver(
            save = { Json.encodeToString(WeatherUiState.serializer(), it.value) },
            restore = { savedString ->
                runCatching {
                    MutableStateFlow(Json.decodeFromString(WeatherUiState.serializer(), savedString))
                }.getOrNull()
            }
        ),
        init = {
            MutableStateFlow(WeatherUiState.Loading(weather = null))
        }
    )
    @OptIn(InternalSerializationApi::class)
    val uiState: StateFlow<WeatherUiState> get() = _uiState.asStateFlow()

    private val areas: List<String> = listOf(
        "東京",
        "神奈川",
        "千葉",
        "埼玉",
        "栃木",
        "群馬",
        "茨城"
    )

    @OptIn(InternalSerializationApi::class)
    fun getWeather() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = WeatherUiState.Loading((_uiState.value as? WeatherUiState.Display)?.weather)

        useCase.get(
            area = areas.random(),
            onSuccess = { weather ->
                _uiState.value = WeatherUiState.Display(weather = weather, showErrorDialog = false)
            },
            onFailure = { _ ->
                _uiState.value = (_uiState.value as? WeatherUiState.Display)?.copy(showErrorDialog = true)
                    ?: WeatherUiState.Display(weather = null, showErrorDialog = true)
            }
        )
    }

    @OptIn(InternalSerializationApi::class)
    fun dismissErrorDialog() {
        _uiState.value = (_uiState.value as? WeatherUiState.Display)?.copy(showErrorDialog = false) ?: _uiState.value
    }
}
