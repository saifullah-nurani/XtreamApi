package io.github.saifullah.xtream

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

@XtreamDsl
class XtreamClientConfig {

    internal var xtreamAuthCredentials: XtreamAuthCredentials? = null
    internal var httpClientBuilder: (() -> HttpClient)? = null
    internal var httpClientConfig: (HttpClientConfig<*>.() -> Unit)? = null
    var followRedirects = true
    var expectSuccess = false
    var socketTimeoutMillis: Long = 60_000
    var connectTimeoutMillis: Long = 60_000
    var requestTimeoutMillis: Long = 60_000
    var useCache: Boolean = true
    var maxRetries: Int = 0
    var useTimeout: Boolean = true
    var retryDelayMillis: Long = 1000

    fun auth(credentials: XtreamAuthCredentials.() -> Unit) {
        this.xtreamAuthCredentials = XtreamAuthCredentials().apply(credentials)
    }

    fun httpClient(block: HttpClientConfig<*> .() -> Unit) {
        this.httpClientConfig = block
    }

    fun <T : HttpClientEngineConfig> httpClient(
        engine: HttpClientEngineFactory<T>, block: HttpClientConfig<T>.() -> Unit = {}
    ) {
        httpClientBuilder = {
            HttpClient(engine, block)
        }
    }
}

