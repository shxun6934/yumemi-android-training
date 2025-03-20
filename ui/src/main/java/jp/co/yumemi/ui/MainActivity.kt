package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        Scaffold { padding ->
            Text(
                text = "Hello World!",
                modifier = Modifier.padding(padding)
            )
        }
    }
}
