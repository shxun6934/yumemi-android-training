package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.ui.design.WeatherTheme
import jp.co.yumemi.ui.weather.ActionButtons
import jp.co.yumemi.ui.weather.WeatherErrorDialog
import jp.co.yumemi.ui.weather.WeatherInfo
import jp.co.yumemi.ui.weather.WeatherUiState
import jp.co.yumemi.use_case.weather.GetWeatherUseCase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherTheme {
                WeatherTopScreen()
            }
        }
    }

    @Composable
    private fun WeatherTopScreen() {
        var uiState: WeatherUiState by remember { mutableStateOf(WeatherUiState.Loading) }
        val useCase = GetWeatherUseCase(LocalContext.current)

        val systemUiController = rememberSystemUiController()
        val statusBarColor = MaterialTheme.colors.primaryVariant

        DisposableEffect(systemUiController) {
            systemUiController.setStatusBarColor(color = statusBarColor)

            onDispose {}
        }

        WeatherTopContent(
            uiState = uiState,
            onClickReload = {
                useCase.get(
                    onSuccess = { weather ->
                        uiState = successWeatherUiState(weather)
                    },
                    onFailure = { _ ->
                        uiState = failedWeatherUiState(uiState as? WeatherUiState.Display)
                    }
                )
            },
            onClickNext = {
                // TODO: Handle next action
            },
            onDismiss = {
                uiState = (uiState as? WeatherUiState.Display)?.copy(showErrorDialog = false) ?: uiState
            }
        )

        LaunchedEffect(true) {
            useCase.get(
                onSuccess = { weather ->
                    uiState = successWeatherUiState(weather)
                },
                onFailure = {
                    uiState = failedWeatherUiState(null)
                }
            )
        }
    }

    @Composable
    private fun WeatherTopContent(
        uiState: WeatherUiState,
        onClickReload: () -> Unit,
        onClickNext: () -> Unit,
        onDismiss: () -> Unit,
        onConfirm: () -> Unit = onClickReload
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                )
            }
        ) { padding ->
            (uiState as? WeatherUiState.Display)?.let {
                val weather = it.weather
                val showErrorDialog = it.showErrorDialog

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherInfo(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        weather = weather
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                    ActionButtons(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        onReload = onClickReload,
                        onNext = onClickNext
                    )
                }

                if (showErrorDialog) {
                    WeatherErrorDialog(
                        onDismiss = onDismiss,
                        onConfirm = onConfirm
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun PreviewWeatherTopContent() {
        val uiState = WeatherUiState.Display(weather = Weather.SUNNY, showErrorDialog = false)

        WeatherTheme {
            WeatherTopContent(
                uiState = uiState,
                onClickReload = {},
                onClickNext = {},
                onDismiss = {}
            )
        }
    }

    private fun successWeatherUiState(weather: Weather): WeatherUiState.Display =
        WeatherUiState.Display(weather = weather, showErrorDialog = false)

    private fun failedWeatherUiState(uiState: WeatherUiState.Display?): WeatherUiState.Display =
        uiState?.copy(showErrorDialog = true)
            ?: WeatherUiState.Display(weather = null, showErrorDialog = true)
}
