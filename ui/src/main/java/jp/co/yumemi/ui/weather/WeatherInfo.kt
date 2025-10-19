package jp.co.yumemi.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.ui.R
import jp.co.yumemi.ui.design.WeatherTheme

@Composable
fun WeatherInfo(
    modifier: Modifier = Modifier,
    weather: Weather?
) {
    Column(modifier = modifier) {
        if (weather != null) {
            Image(
                painter = painterResource(
                    id = when (weather) {
                        Weather.SUNNY -> R.drawable.ic_sunny
                        Weather.CLOUDY -> R.drawable.ic_cloudy
                        Weather.RAINY -> R.drawable.ic_rainy
                        else -> R.drawable.ic_snow
                    }
                ),
                contentDescription = stringResource(id = R.string.weather_image_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = colorResource(
                        id = when (weather) {
                            Weather.SUNNY -> R.color.sunny
                            Weather.CLOUDY -> R.color.cloudy
                            Weather.RAINY -> R.color.rainy
                            else -> R.color.snow
                        }
                    )
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "text1",
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "text2",
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSunnyWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = Weather.SUNNY
        )
    }
}

@Preview
@Composable
private fun PreviewCloudyWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = Weather.CLOUDY
        )
    }
}

@Preview
@Composable
private fun PreviewRainyWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = Weather.RAINY
        )
    }
}

@Preview
@Composable
private fun PreviewSnowWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = Weather.SNOW
        )
    }
}
