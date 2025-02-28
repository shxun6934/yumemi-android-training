package jp.co.yumemi.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.ui.R
import jp.co.yumemi.use_case.weather.GetWeatherUseCase

class WeatherTopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_weather_top, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val useCase = GetWeatherUseCase(context)

        getCurrentWeather(useCase, context, view)

        view.findViewById<Button>(R.id.reload_button).setOnClickListener {
            getCurrentWeather(useCase, context, view)
        }
    }

    private fun getCurrentWeather(useCase: GetWeatherUseCase, context: Context, view: View) {
        useCase.get(
            onSuccess = { currentWeather ->
                setWeatherImage(view, currentWeather)
            },
            onFailure = { _ ->
                val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_message)
                    .setNegativeButton(R.string.close) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.reload) { dialog, _ ->
                        getCurrentWeather(useCase, context, view)
                        dialog.dismiss()
                    }
                    .create()
                dialog.show()
            }
        )
    }

    private fun setWeatherImage(view: View, currentWeather: Weather) {
        view.findViewById<ImageView>(R.id.weather_image).apply {
            setImageResource(
                when (currentWeather) {
                    Weather.SUNNY -> R.drawable.ic_sunny
                    Weather.CLOUDY -> R.drawable.ic_cloudy
                    Weather.RAINY -> R.drawable.ic_rainy
                    else -> R.drawable.ic_snow
                }
            )
            setColorFilter(
                resources.getColor(
                    when (currentWeather) {
                        Weather.SUNNY -> R.color.sunny
                        Weather.CLOUDY -> R.color.cloudy
                        Weather.RAINY -> R.color.rainy
                        else -> R.color.snow
                    },
                    null
                )
            )
        }
    }
}
