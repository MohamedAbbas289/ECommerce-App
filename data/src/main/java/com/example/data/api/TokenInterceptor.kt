package com.example.data.api

import com.example.domain.repositories.sessionManager.SessionManagerRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val sessionManagerRepository: SessionManagerRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionManagerRepository.getUserData()?.token
        val newRequestBuilder = chain.request().newBuilder()
        token?.let {
            newRequestBuilder
                .addHeader("token", token)
        }

        return chain.proceed(newRequestBuilder.build())
    }
}