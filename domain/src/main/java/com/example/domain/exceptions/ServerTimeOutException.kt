package com.example.domain.exceptions

import java.io.IOException

class ServerTimeOutException(
    message: String,
    ex: Throwable
) : IOException(message, ex) {
}