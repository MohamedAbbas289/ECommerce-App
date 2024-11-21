package com.example.data

import android.util.Log
import com.example.data.model.BaseResponse
import com.example.domain.common.ResultWrapper
import com.example.domain.exceptions.ParsingException
import com.example.domain.exceptions.ServerError
import com.example.domain.exceptions.ServerTimeOutException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<ResultWrapper<T>> = flow {
    emit(ResultWrapper.Loading)

    val result = apiCall.invoke()
    emit(ResultWrapper.Success(result))


}.flowOn(Dispatchers.IO)
    .catch { e ->
        when (e) {
            is SocketTimeoutException -> {
                emit(
                    ResultWrapper.Error(
                        ServerTimeOutException(e.message ?: "TimeOut", e)
                    )
                )
                Log.d("GTAG", "timeout exception: ${e.message}")
            }

            is IOException -> {
                emit(
                    ResultWrapper.Error(
                        ServerTimeOutException(e.message ?: "IO Exception", e)
                    )
                )
                Log.d("GTAG", "IO exception: ${e.message}")
            }

            is HttpException -> {
                val body = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(body, BaseResponse::class.java)
                emit(
                    ResultWrapper.ServerError(
                        ServerError(
                            response.statusMsg ?: "",
                            response.message ?: "",
                            e.code(),
                            e
                        )
                    )
                )
                Log.d("GTAG", "HttpException: ${e.message}")
            }

            is JsonSyntaxException -> {
                emit(
                    ResultWrapper.Error(
                        ParsingException(e)
                    )
                )
                Log.d("GTAG", "JsonSyntaxException: ${e.message}")
            }

            else -> {
                emit(ResultWrapper.Error(e))
                Log.d("GTAG", "else exception: ${e.message}")
            }
        }

    }
