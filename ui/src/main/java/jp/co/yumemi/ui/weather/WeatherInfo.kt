package jp.co.yumemi.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.model.weather.WeatherResponse
import jp.co.yumemi.ui.R
import jp.co.yumemi.ui.design.WeatherTheme

@Composable
fun WeatherInfo(
    modifier: Modifier = Modifier,
    weather: WeatherResponse?
) {
    Column(modifier = modifier) {
        if (weather != null) {
            Text(
                text = weather.area,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(
                    id = when (weather.weather) {
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
                        id = when (weather.weather) {
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
                    text = stringResource(R.string.temperature, weather.minTemp),
                    modifier = Modifier
                        .weight(1f),
                    color = Color.Blue,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.temperature, weather.maxTemp),
                    modifier = Modifier
                        .weight(1f),
                    color = Color.Red,
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
            weather = WeatherResponse(
                weather = Weather.SUNNY,
                maxTemp = 30,
                minTemp = 25,
                date = "2020-04-01T12:00",
                area = "東京"
            )
        )
    }
}

@Preview
@Composable
private fun PreviewCloudyWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = WeatherResponse(
                weather = Weather.CLOUDY,
                maxTemp = 27,
                minTemp = 16,
                date = "2020-04-01T12:00",
                area = "神奈川"
            )
        )
    }
}

@Preview
@Composable
private fun PreviewRainyWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = WeatherResponse(
                weather = Weather.RAINY,
                maxTemp = 17,
                minTemp = 14,
                date = "2020-04-01T12:00",
                area = "埼玉"
            )
        )
    }
}

@Preview
@Composable
private fun PreviewSnowWeatherInfo() {
    WeatherTheme {
        WeatherInfo(
            weather = WeatherResponse(
                weather = Weather.SNOW,
                maxTemp = 4,
                minTemp = -2,
                date = "2020-04-01T12:00",
                area = "千葉"
            )
        )
    }
}
