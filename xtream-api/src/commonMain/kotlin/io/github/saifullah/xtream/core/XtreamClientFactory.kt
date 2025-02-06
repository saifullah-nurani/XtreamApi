package io.github.saifullah.xtream.core

import io.github.saifullah.xtream.XtreamClientConfig
import io.github.saifullah.xtream.url.urlBuilder
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal interface XtreamClientFactory {

    companion object {
        fun create(clientConfig: XtreamClientConfig): HttpClient {
            val httpClientConfig: HttpClientConfig<*>.() -> Unit = {
                expectSuccess = clientConfig.expectSuccess
                followRedirects = clientConfig.followRedirects
                defaultRequest {
                    val credentials = clientConfig.xtreamAuthCredentials
                    if (credentials != null) {
                        url(credentials.urlBuilder().buildString())
                    }
                    header(HttpHeaders.Accept, ContentType.Application.Json)
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    header(HttpHeaders.ContentType, ContentType.Video.Any)
                }
                if (clientConfig.useTimeout) {
                    install(HttpTimeout) {
                        socketTimeoutMillis = clientConfig.socketTimeoutMillis
                        connectTimeoutMillis = clientConfig.connectTimeoutMillis
                        requestTimeoutMillis = clientConfig.requestTimeoutMillis
                    }
                }
                install(ContentNegotiation) {
                    json(Json {
                        explicitNulls = true
                        ignoreUnknownKeys = true
                        prettyPrint = false
                        encodeDefaults = false
                        isLenient = true
                    })
                }

                if (clientConfig.useCache) {
                    install(HttpCache)
                }
                if (clientConfig.maxRetries > 0) {
                    install(HttpRequestRetry) {
                        maxRetries = clientConfig.maxRetries
                        delayMillis {
                            clientConfig.retryDelayMillis
                        }
                    }
                }
            }
            return clientConfig.httpClientBuilder?.invoke()?.config(httpClientConfig)
                ?: io.github.saifullah.xtream.core.HttpClient(httpClientConfig)
        }
    }
}

internal expect fun HttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient
