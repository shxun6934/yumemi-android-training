package jp.co.yumemi.model.weather

import kotlinx.serialization.Serializable

@Serializable
enum class Weather {
    SUNNY,
    CLOUDY,
    RAINY,
    SNOW
}
