package io.github.saifullah.xtream.url

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parameters

internal fun XtreamAuthCredentials.urlBuilder(): URLBuilder {
    return URLBuilder(
        port = this@urlBuilder.port,
        host = this@urlBuilder.host.replace("^http[s]?://".toRegex(), ""),
        protocol = when (this@urlBuilder.protocol.lowercase()) {
            "http" -> URLProtocol.HTTP
            "https" -> URLProtocol.HTTPS
            else -> URLProtocol.HTTPS
        },
        parameters = parameters {
            append("username", username)
            append("password", password)
        },
        pathSegments = listOf("player_api.php")
    )
}