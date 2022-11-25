package io.vlk.livesporttask.network

sealed class ResponseState<T> {
    data class Success<T>(val value: T) : ResponseState<T>()
    data class Error<T>(val message: String) : ResponseState<T>()
    class Loading<T> : ResponseState<T>()
}