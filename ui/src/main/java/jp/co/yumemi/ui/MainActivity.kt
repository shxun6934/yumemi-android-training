package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                )
            }
        ) { padding ->
            Text(
                text = "Hello World!",
                modifier = Modifier.padding(padding)
            )
        }
    }
}
