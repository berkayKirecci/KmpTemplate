package com.example.kmptemplate.network

import com.example.kmptemplate.network.model.BaseResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause)

suspend inline fun <reified T : BaseResponse> safeRequest(
    crossinline suspendCall: suspend () -> HttpResponse
): T = withContext(Dispatchers.IO) {
    try {
        val httpResponse = suspendCall()
        val statusCode = httpResponse.status.value
        val body = httpResponse.body<T>()
        if (statusCode in 200..299) {
            if (body.isError == true) {
                throw NetworkException(body.errorMessage ?: "Unknown error")
            }
            body
        } else {
            throw NetworkException(body.errorMessage ?: "HTTP $statusCode")
        }
    } catch (e: NetworkException) {
        throw e
    } catch (e: Exception) {
        throw NetworkException("Connection error", e)
    }
}

inline fun <reified T : BaseResponse> safeFlowRequest(
    crossinline suspendCall: suspend () -> HttpResponse
): Flow<T> = flow {
    val httpResponse = suspendCall()
    val statusCode = httpResponse.status.value
    val body = httpResponse.body<T>()
    if (statusCode in 200..299) {
        if (body.isError == true) {
            throw NetworkException(body.errorMessage ?: "Unknown error")
        } else {
            emit(body)
        }
    } else {
        throw NetworkException(body.errorMessage ?: "HTTP $statusCode")
    }
}.catch { cause ->
    throw NetworkException("Connection error", cause)
}.flowOn(Dispatchers.IO)