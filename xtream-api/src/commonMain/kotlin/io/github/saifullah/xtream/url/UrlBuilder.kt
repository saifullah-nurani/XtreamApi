package io.github.saifullah.xtream.url

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.http.takeFrom

internal fun XtreamAuthCredentials.urlBuilder(): URLBuilder {

    require(host.isNotBlank()) { "host cannot be blank" }

    val normalizedHost =
        if (host.startsWith("http://", true) || host.startsWith("https://", true)) {
            host
        } else {
            "${protocol.ifBlank { "https" }}://$host"
        }

    val builder = URLBuilder().takeFrom(normalizedHost)

    // Override port only if user provided one
    if (port > 0) {
        builder.port = port
    }

    builder.pathSegments = listOf("player_api.php")

    builder.parameters.append("username", username)
    builder.parameters.append("password", password)

    return builder
}
internal fun URLBuilder.withAction(action: String): URLBuilder {
    parameters.append("action", action)
    return this
}