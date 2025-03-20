package jp.co.yumemi.ui.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.ui.R

object WeatherExtension {
    fun Weather.image(context: Context): Drawable? = getDrawable(
        context,
        when (this) {
            Weather.SUNNY -> R.drawable.ic_sunny
            Weather.CLOUDY -> R.drawable.ic_cloudy
            Weather.RAINY -> R.drawable.ic_rainy
            else -> R.drawable.ic_snow
        }
    )

    fun Weather.color(): Int = when (this) {
        Weather.SUNNY -> R.color.sunny
        Weather.CLOUDY -> R.color.cloudy
        Weather.RAINY -> R.color.rainy
        else -> R.color.snow
    }
}
