package com.example.domain.exceptions

class AuthenticationException(
    message: String,
    cause: Throwable?
) : Throwable(message, cause)