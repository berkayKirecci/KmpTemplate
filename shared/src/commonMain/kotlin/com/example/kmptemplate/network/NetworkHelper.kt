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

inline fun <reified T : BaseResponse> safeFlowRequest(
    crossinline suspendCall: suspend () -> HttpResponse
): Flow<Result<T>> = flow {
    val httpResponse = suspendCall()
    val statusCode = httpResponse.status.value
    val body = httpResponse.body<T>()
    if (statusCode in 200..299) {
        if (body.isError == true) {
            emit(Result.failure(Exception(body.errorMessage ?: "Bilinmeyen Bir Hata Oluştu")))
        } else {
            emit(Result.success(httpResponse.body<T>()))
        }
    } else {
        emit(Result.failure(Exception(body.errorMessage ?: "Bilinmeyen Bir Hata Oluştu")))
    }
}.catch { throwable ->
    emit(Result.failure(Exception("Bağlantınızda Bir Sorun Oluştu")))
}.flowOn(Dispatchers.IO)