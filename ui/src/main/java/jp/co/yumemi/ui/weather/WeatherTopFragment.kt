package jp.co.yumemi.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import jp.co.yumemi.model.weather.Weather
import jp.co.yumemi.ui.R
import jp.co.yumemi.ui.databinding.FragmentWeatherTopBinding
import jp.co.yumemi.use_case.weather.GetWeatherUseCase

class WeatherTopFragment : Fragment() {
    private lateinit var binding: FragmentWeatherTopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val useCase = GetWeatherUseCase(context)

        getCurrentWeather(useCase, context)

        binding.reloadButton.setOnClickListener {
            getCurrentWeather(useCase, context)
        }
    }

    private fun getCurrentWeather(useCase: GetWeatherUseCase, context: Context) {
        useCase.get(
            onSuccess = { currentWeather ->
                setWeatherImage(currentWeather)
            },
            onFailure = { _ ->
                val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_message)
                    .setNegativeButton(R.string.close) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.reload) { dialog, _ ->
                        getCurrentWeather(useCase, context)
                        dialog.dismiss()
                    }
                    .create()
                dialog.show()
            }
        )
    }

    private fun setWeatherImage(currentWeather: Weather) {
        binding.weatherImage.apply {
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
