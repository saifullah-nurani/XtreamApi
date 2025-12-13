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
     * @param credentials Optional `XtreamAuthCredentials` object for
     *    authenticated requests.
     * @param action Optional action query parameter to append.
     * @param parameterBuilder Lambda to append additional query parameters to
     *    the URL.
     */
    fun HttpRequestBuilder.url(
        credentials: XtreamAuthCredentials?,
        action: String? = null,
        parameterBuilder: ParametersBuilder.() -> Unit = {}
    ) {
        val isValidCredentials = credentials?.isValid() ?: false

        if (isValidCredentials) {
            val urlBuilder = credentials!!.urlBuilder()
            action?.let { urlBuilder.parameters.append("action", it) }
            urlBuilder.parameters.parameterBuilder()
            url(urlBuilder.buildString())
        } else {
            // Fallback to default URL with action parameter
            url {
                action?.let { parameters.append("action", it) }
                parameterBuilder()
            }
        }
    }
}
