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
): Flow<T> = flow {
    val httpResponse = suspendCall()
    val statusCode = httpResponse.status.value
    val body = httpResponse.body<T>()
    if (statusCode in 200..299) {
        if (body.isError == true) {
            throw Exception(body.errorMessage ?: "Bilinmeyen Bir Hata Oluştu")
        } else {
            emit(body)
        }
    } else {
        throw Exception(body.errorMessage ?: "Bilinmeyen Bir Hata Oluştu")
    }
}.catch { _ ->
    throw Exception("Bağlantınızda Bir Sorun Oluştu")
}.flowOn(Dispatchers.IO)