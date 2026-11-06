package jp.co.yumemi.model.weather

import kotlinx.serialization.Serializable

/**
 * 取得した天気
 *
 * @property weather 天気（sunny, cloudy, rainy, snow）
 * @property maxTemp 最高気温（例：20）
 * @property minTemp 最低気温（例：-20）
 * @property date ISO8601形式の文字列型日付（例：2020-04-01T12:00）
 * @property area エリア（例：東京）
 */
@Serializable
data class WeatherResponse(
    val weather: Weather,
    val maxTemp: Int,
    val minTemp: Int,
    val date: String,
    val area: String
)
