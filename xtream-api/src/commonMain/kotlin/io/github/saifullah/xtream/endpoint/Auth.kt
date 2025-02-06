package io.github.saifullah.xtream.endpoint

import io.github.saifullah.xtream.endpoint.XtreamApi
import io.github.saifullah.xtream.model.XtreamAuth
import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Authentication class for Xtream API.
 *
 * This class handles authentication requests using the provided [HttpClient].
 *
 * @property httpClient The HTTP client used to make network requests.
 */
class Auth internal constructor(override val httpClient: HttpClient) : XtreamApi {

    /**
     * Authenticates the user using the provided credentials.
     *
     * @param credentials Optional [XtreamAuthCredentials] containing authentication details.
     * If null, the default credentials will be used.
     *
     * @return [XtreamAuth] containing authentication response details.
     *
     * @throws ClientRequestException if the request fails due to an HTTP error.
     * @throws SerializationException if the response cannot be parsed.
     */
    suspend fun auth(credentials: XtreamAuthCredentials? = null): XtreamAuth {
        return httpClient.get { url(credentials) }.body<XtreamAuth>()
    }
}
