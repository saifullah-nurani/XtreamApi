package io.github.saifullah.xtream

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
@XtreamDsl
class XtreamClientConfig {

    internal var xtreamAuthCredentials: XtreamAuthCredentials? = null

    internal var httpClient: HttpClient? = null
    internal var httpClientBuilder: (() -> HttpClient)? = null

    var followRedirects = true
    var expectSuccess = false

    var socketTimeoutMillis: Long = 60_000
        set(value) {
            require(value > 0) { "socketTimeoutMillis must be positive" }
            field = value
        }

    var connectTimeoutMillis: Long = 60_000
        set(value) {
            require(value > 0) { "connectTimeoutMillis must be positive" }
            field = value
        }

    var requestTimeoutMillis: Long = 60_000
        set(value) {
            require(value > 0) { "requestTimeoutMillis must be positive" }
            field = value
        }
    var useTimeout: Boolean = true
    var useCache: Boolean = true

    var maxRetries: Int = 0
        set(value) {
            require(value >= 0) { "maxRetries must be non-negative" }
            field = value
        }

    var retryDelayMillis: Long = 1000
        set(value) {
            require(value >= 0) { "retryDelayMillis must be non-negative" }
            field = value
        }

    fun auth(credentials: XtreamAuthCredentials.() -> Unit) {
        xtreamAuthCredentials = XtreamAuthCredentials().apply(credentials)
    }


    fun httpClient(client: HttpClient) {
        this.httpClient = client
    }


    fun <T : HttpClientEngineConfig> httpClient(
        engine: HttpClientEngineFactory<T>,
        block: HttpClientConfig<T>.() -> Unit = {}
    ) {
        httpClientBuilder = {
            HttpClient(engine, block)
        }
    }
}

