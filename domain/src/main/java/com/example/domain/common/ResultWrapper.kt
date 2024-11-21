package com.example.domain.common


sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data object Loading : ResultWrapper<Nothing>()
    data class ServerError(
        val serverError: com.example.domain.exceptions.ServerError
    ) : ResultWrapper<Nothing>()

    data class Error(
        val error: Throwable?
    ) : ResultWrapper<Nothing>()
}