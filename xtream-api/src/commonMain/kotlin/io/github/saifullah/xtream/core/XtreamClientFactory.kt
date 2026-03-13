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
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendAll
import kotlinx.serialization.json.Json

internal object XtreamClientFactory {

    fun create(clientConfig: XtreamClientConfig): HttpClient {

        val httpClientConfig: HttpClientConfig<*>.() -> Unit = {

            expectSuccess = clientConfig.expectSuccess
            followRedirects = clientConfig.followRedirects

            defaultRequest {

                val credentials = clientConfig.xtreamAuthCredentials

                if (credentials != null) {
                    val base = credentials.urlBuilder()

                    url {
                        protocol = base.protocol
                        host = base.host
                        port = base.port
                        encodedPath = base.encodedPath
                        parameters.appendAll(base.parameters)
                    }
                }

                header(HttpHeaders.Accept, ContentType.Application.Json)
                header(HttpHeaders.UserAgent, "XtreamApi")
            }

            if (clientConfig.useTimeout) {
                install(HttpTimeout) {
                    socketTimeoutMillis = clientConfig.socketTimeoutMillis
                    connectTimeoutMillis = clientConfig.connectTimeoutMillis
                    requestTimeoutMillis = clientConfig.requestTimeoutMillis
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                        isLenient = true
                    }
                )
            }

            if (clientConfig.useCache) {
                install(HttpCache)
            }

            if (clientConfig.maxRetries > 0) {
                install(HttpRequestRetry) {
                    maxRetries = clientConfig.maxRetries
                    retryIf { _, response ->
                        !response.status.isSuccess()
                    }
                    delayMillis { clientConfig.retryDelayMillis }
                }
            }
        }

        val client = clientConfig.httpClientBuilder?.invoke()
            ?: HttpClient {}

        return client.config(httpClientConfig)
    }
}

internal expect fun HttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient
