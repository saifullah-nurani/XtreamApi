package io.github.saifullah.xtream.url

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parameters

internal fun XtreamAuthCredentials.urlBuilder(): URLBuilder {
    // Remove protocol prefix if present in host
    val cleanHost = host.replace(Regex("^https?://"), "")
    
    val protocol = when (this.protocol.lowercase()) {
        "http" -> URLProtocol.HTTP
        "https" -> URLProtocol.HTTPS
        else -> URLProtocol.HTTPS
    }
    
    return URLBuilder(
        port = port,
        host = cleanHost,
        protocol = protocol,
        parameters = parameters {
            append("username", username)
            append("password", password)
        },
        pathSegments = listOf("player_api.php")
    )
}