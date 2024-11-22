package com.example.domain.exceptions


class ParsingException(
    e: Throwable
) : Throwable(e.message, e) {
}