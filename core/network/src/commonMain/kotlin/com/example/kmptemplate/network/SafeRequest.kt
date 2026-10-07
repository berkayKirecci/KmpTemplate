package com.example.kmptemplate.network

import com.example.kmptemplate.base.AppError
import com.example.kmptemplate.base.AppErrorAware
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

class NetworkException(
    override val appError: AppError,
    override val serverMessage: String? = null,
    cause: Throwable? = null,
) : Exception(serverMessage ?: appError.name, cause), AppErrorAware

suspend inline fun <reified T : BaseResponse> safeRequest(
    crossinline suspendCall: suspend () -> HttpResponse
): T = withContext(Dispatchers.IO) {
    try {
        val httpResponse = suspendCall()
        val statusCode = httpResponse.status.value
        val body = httpResponse.body<T>()
        if (statusCode in 200..299) {
            if (body.isError == true) {
                throw NetworkException(AppError.SERVER, body.errorMessage)
            }
            body
        } else {
            throw NetworkException(AppError.SERVER, body.errorMessage ?: "HTTP $statusCode")
        }
    } catch (e: NetworkException) {
        throw e
    } catch (e: Exception) {
        throw NetworkException(AppError.CONNECTION, cause = e)
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
            throw NetworkException(AppError.SERVER, body.errorMessage)
        } else {
            emit(body)
        }
    } else {
        throw NetworkException(AppError.SERVER, body.errorMessage ?: "HTTP $statusCode")
    }
}.catch { cause ->
    throw NetworkException(AppError.CONNECTION, cause = cause)
}.flowOn(Dispatchers.IO)