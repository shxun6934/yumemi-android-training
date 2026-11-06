package jp.co.yumemi.model.weather

import kotlinx.serialization.Serializable

/**
 * 天気を取得するためのパラメータ
 *
 * @property area エリア（例：東京）
 * @property date ISO8601形式の文字列型日付（例：2020-04-01T12:00）
 */
@Serializable
data class WeatherRequest(
    val area: String,
    val date: String
)
