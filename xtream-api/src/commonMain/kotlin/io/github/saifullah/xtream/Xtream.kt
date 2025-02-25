package io.github.saifullah.xtream

import io.github.saifullah.xtream.core.XtreamClientFactory
import io.github.saifullah.xtream.endpoint.Auth
import io.github.saifullah.xtream.endpoint.Custom
import io.github.saifullah.xtream.endpoint.Movie
import io.github.saifullah.xtream.endpoint.TvSeries
import io.ktor.client.HttpClient

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
internal annotation class XtreamDsl

@XtreamDsl
fun Xtream(block: XtreamClientConfig.() -> Unit): Xtream {
    val clientConfig = XtreamClientConfig().apply(block)
    return Xtream(clientConfig)
}

class Xtream internal constructor(clientConfig: XtreamClientConfig) {
    private val httpClient: HttpClient by lazy {
        XtreamClientFactory.create(clientConfig)
    }

    val auth by endPoint(::Auth)
    val movie by endPoint(::Movie)
    val tvSeries by endPoint(::TvSeries)
    val custom by endPoint(::Custom)

    private inline fun <T> endPoint(crossinline client: HttpClient.() -> T) = lazy {
        client.invoke(httpClient)
    }
}