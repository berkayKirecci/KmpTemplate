package com.example.kmptemplate.network

import com.example.kmptemplate.network.model.BaseRequest
import com.example.kmptemplate.network.model.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class NetworkClient internal constructor(private val httpClient: HttpClient) {

    suspend fun get(endPoint: String) = httpClient.get(endPoint)

    suspend fun post(endPoint: String, body: BaseRequest) = httpClient.post(endPoint) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }

    inline fun <reified T : BaseResponse> get(endPoint: String): Flow<T> =
        safeFlowRequest { get(endPoint) }

    inline fun <reified T : BaseResponse> post(endPoint: String, body: BaseRequest): Flow<T> =
        safeFlowRequest { post(endPoint, body) }
}

fun createNetworkClient() = NetworkClient(createHttpClient())

internal fun createHttpClient() = HttpClient {
    defaultRequest {
        url {
            takeFrom("https://dummyjson.com/")
            protocol = URLProtocol.HTTPS
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 10_000
        socketTimeoutMillis = 15_000
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        })
    }

    install(Logging) {
        level = LogLevel.BODY
        logger = object : Logger {
            override fun log(message: String) {
                println("[HttpClient] $message")
            }
        }
    }
}

