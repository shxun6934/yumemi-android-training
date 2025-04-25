package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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

                ConstraintLayout(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    val (weatherInfo, actionButtons) = createRefs()

                    WeatherInfo(
                        modifier = Modifier.constrainAs(weatherInfo) {
                            linkTo(top = parent.top, bottom = parent.bottom)
                            linkTo(start = parent.start, end = parent.end)
                            width = Dimension.percent(0.5f)
                            height = Dimension.wrapContent
                        },
                        weather = weather
                    )
                    ActionButtons(
                        modifier = Modifier.constrainAs(actionButtons) {
                            top.linkTo(weatherInfo.bottom, margin = 80.dp)
                            linkTo(start = weatherInfo.start, end = weatherInfo.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        },
                        onReload = {
                            useCase.get(
                                onSuccess = { weather ->
                                    uiState = successWeatherUiState(weather)
                                },
                                onFailure = { _ ->
                                    uiState = failedWeatherUiState(it)
                                }
                            )
                        },
                        onNext = {}
                    )
                }

                if (showErrorDialog) {
                    WeatherErrorDialog(
                        onDismiss = {
                            uiState = it.copy(showErrorDialog = false)
                        },
                        onConfirm = {
                            useCase.get(
                                onSuccess = { weather ->
                                    uiState = successWeatherUiState(weather)
                                },
                                onFailure = { _ ->
                                    uiState = failedWeatherUiState(it)
                                }
                            )
                        }
                    )
                }
            }
        }

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

    private fun successWeatherUiState(weather: Weather): WeatherUiState.Display =
        WeatherUiState.Display(weather = weather, showErrorDialog = false)

    private fun failedWeatherUiState(uiState: WeatherUiState.Display?): WeatherUiState.Display =
        uiState?.copy(showErrorDialog = true)
            ?: WeatherUiState.Display(weather = null, showErrorDialog = true)
}
