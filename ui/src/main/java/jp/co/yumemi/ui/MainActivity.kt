package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import jp.co.yumemi.ui.design.WeatherTheme

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
        val systemUiController = rememberSystemUiController()
        val statusBarColor = MaterialTheme.colors.primaryVariant

        DisposableEffect(systemUiController) {
            systemUiController.setStatusBarColor(color = statusBarColor)

            onDispose {}
        }

        WeatherTopContent(
            onClickReload = {
                // TODO: Handle reload action
            },
            onClickNext = {
                // TODO: Handle next action
            }
        )
    }

    @Composable
    private fun WeatherTopContent(
        onClickReload: () -> Unit,
        onClickNext: () -> Unit
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sunny),
                    contentDescription = stringResource(id = R.string.weather_image_description),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.sunny))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "text1",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "text2",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Button(
                        onClick = onClickReload,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = stringResource(id = R.string.RELOAD))
                    }
                    Button(
                        onClick = onClickNext,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = R.string.NEXT))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun PreviewWeatherTopContent() {
        WeatherTheme {
            WeatherTopContent(
                onClickReload = {},
                onClickNext = {}
            )
        }
    }
}
