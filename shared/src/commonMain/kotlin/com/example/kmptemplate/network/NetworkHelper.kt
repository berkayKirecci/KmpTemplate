package com.example.kmptemplate.network

import com.example.kmptemplate.network.model.ApiResponseWrapper
import com.example.kmptemplate.network.model.BaseResponse
import com.example.kmptemplate.network.model.ErrorCodes
import com.example.kmptemplate.network.model.ErrorCodes.RANGE_5XX
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <reified T : BaseResponse> safeFlowRequest(
    crossinline suspendCall: suspend () -> HttpResponse
): Flow<ApiResponseWrapper<T>> = flow {
    val result = try {
        val response = suspendCall()
        val code = response.status.value
        if (code in 200..299) {
            ApiResponseWrapper.Success(response.body<T>())
        } else {
            ApiResponseWrapper.Error(errorMessage(code))
        }
    } catch (e: Exception) {
        ApiResponseWrapper.Error(errorMessage(ErrorCodes.CONNECTION, e.message))
    }
    emit(result)
}.flowOn(Dispatchers.IO)

fun errorMessage(code: Int, details: String? = null): String = when (code) {
    ErrorCodes.CONNECTION -> "Connection Error${details?.let { ": $it" }.orEmpty()}"
    ErrorCodes.BAD_REQUEST -> "Bad Request"
    ErrorCodes.UNAUTHORIZED -> "Unauthorized - Please login again"
    ErrorCodes.NOT_FOUND -> "Resource Not Found"
    ErrorCodes.FORBIDDEN -> "Access Forbidden"
    ErrorCodes.TOO_MANY_REQUESTS -> "Too Many Requests - Please slow down"
    in RANGE_5XX -> "Server Error - Please try again later"
    else -> "Unknown Error (Code: $code)"
}
