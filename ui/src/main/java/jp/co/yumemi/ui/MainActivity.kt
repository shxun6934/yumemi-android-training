package jp.co.yumemi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
            ConstraintLayout(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                val (weatherImage, text1, text2, reloadButton, nextButton) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_sunny),
                    contentDescription = stringResource(id = R.string.weather_image_description),
                    modifier = Modifier.constrainAs(weatherImage) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        linkTo(start = parent.start, end = parent.end)
                        width = Dimension.percent(0.5f)
                        height = Dimension.ratio("1:1")
                    },
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.sunny))
                )
                Text(
                    text = "text1",
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(weatherImage.bottom)
                        linkTo(start = weatherImage.start, end = text2.start)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "text2",
                    modifier = Modifier.constrainAs(text2) {
                        top.linkTo(weatherImage.bottom)
                        linkTo(start = text1.end, end = weatherImage.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {},
                    modifier = Modifier.constrainAs(reloadButton) {
                        top.linkTo(text1.bottom, margin = 80.dp)
                        linkTo(start = text1.start, end = text1.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                ) {
                    Text(text = stringResource(id = R.string.RELOAD))
                }
                Button(
                    onClick = {},
                    modifier = Modifier.constrainAs(nextButton) {
                        top.linkTo(text2.bottom, margin = 80.dp)
                        linkTo(start = text2.start, end = text2.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                ) {
                    Text(text = stringResource(id = R.string.NEXT))
                }
            }
        }
    }
}
