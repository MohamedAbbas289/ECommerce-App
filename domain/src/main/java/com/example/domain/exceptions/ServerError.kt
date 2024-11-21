package com.example.domain.exceptions

class ServerError(
    val status: String,
    val serverMessage: String,
    val statusCode: Int,
    val ex: Throwable?
) : Throwable(serverMessage, ex) {
}