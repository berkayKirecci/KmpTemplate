package com.example.kmptemplate.network

import com.example.kmptemplate.network.model.BaseRequest
import io.ktor.client.HttpClient
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
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class NetworkClient(private val httpClient: HttpClient) {

    suspend fun get(endPoint: String) = httpClient.get(endPoint)

    suspend fun post(endPoint: String, body: BaseRequest) = httpClient.post(endPoint) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
}

val networkModule = module {
    single { createHttpClient() }
    singleOf(::NetworkClient)
}

private fun createHttpClient() = HttpClient {
    defaultRequest {
        url {
            takeFrom("https://dummyjson.com/")
            protocol = URLProtocol.HTTPS
        }
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

