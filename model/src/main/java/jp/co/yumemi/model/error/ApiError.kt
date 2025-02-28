package jp.co.yumemi.model.error

sealed class ApiError : Throwable() {
    data class UnknownException(val e: Throwable) : ApiError()
}
