package jp.co.yumemi.ui.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun WeatherTheme(content: @Composable () -> Unit) {
    val lightColors = lightColors(
        primary = purple500,
        primaryVariant = purple700,
        secondary = teal200,
        secondaryVariant = teal700,
        onPrimary = white,
        onSecondary = black
    )

    val darkColors = darkColors(
        primary = purple200,
        primaryVariant = purple700,
        secondary = teal200,
        secondaryVariant = teal200,
        onPrimary = black,
        onSecondary = black
    )

    val typography = Typography()

    val shapes = Shapes()

    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors else lightColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
