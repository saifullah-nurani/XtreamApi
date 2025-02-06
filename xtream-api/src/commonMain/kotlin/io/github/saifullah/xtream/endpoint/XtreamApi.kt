package io.github.saifullah.xtream.endpoint

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.github.saifullah.xtream.url.urlBuilder
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.ParametersBuilder

/**
 * The XtreamApi interface provides core functionality for building URLs
 * and making HTTP requests using the `HttpClient`.
 *
 * @property httpClient The HTTP client used to make network requests.
 */
internal interface XtreamApi {
    val httpClient: HttpClient

    /**
     * Builds a complete URL based on the provided authentication and
     * parameters.
     *
     * This function uses either the authenticated URL (if valid credentials
     * are provided) or a default URL. It allows appending an optional `action`
     * parameter and additional custom query parameters using a lambda.
     *
     * @param authentication Optional `XtreamAuthentication` object for
     *    authenticated requests.
     * @param action Optional action query parameter to append.
     * @param parameterBuilder Lambda to append additional query parameters to
     *    the URL.
     *    provided or incomplete.
     * @return The fully constructed URL as a string.
     */
    fun HttpRequestBuilder.url(
        credentials: XtreamAuthCredentials?,
        action: String? = null,
        parameterBuilder: ParametersBuilder.() -> Unit = {}
    ) {
        credentials?.takeIf { it.host.isNotBlank() && it.username.isNotBlank() && it.password.isNotBlank() }
            ?.urlBuilder()?.let { newUrl ->
                // Append the action to the authentication-based URL
                action?.let { newUrl.parameters.append("action", it) }
                newUrl.parameters.parameterBuilder()
                url(newUrl.buildString())
            } ?: run {
            // Fallback to default URL with action parameter
            url {
                action?.let { it1 -> parameters.append("action", it1) }
                parameters.parameterBuilder()
            }
        }
    }
}
