package io.github.saifullah.xtream.endpoint

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Url

/**
 * A helper class for making HTTP requests using Ktor's HttpClient.
 * Users can rename this class based on their specific use case.
 *
 * @property httpClient The configured HttpClient instance used for network requests.
 */
class Custom internal constructor(override val httpClient: HttpClient) : XtreamApi {

    /**
     * Performs a GET request with a custom request builder.
     * @param block The configuration block for the HTTP request.
     * @return The HttpResponse object containing the response data.
     */
    suspend fun get(block: HttpRequestBuilder.() -> Unit): HttpResponse {
        return httpClient.get(block)
    }

    /**
     * Performs a GET request using an existing HttpRequestBuilder.
     * @param builder The pre-configured request builder.
     * @return The HttpResponse object.
     */
    suspend fun get(builder: HttpRequestBuilder): HttpResponse {
        return httpClient.get(builder = builder)
    }

    /**
     * Performs a GET request to a specified URL string with additional request configurations.
     * @param urlString The target URL as a string.
     * @param block The request configuration block.
     * @return The HttpResponse object.
     */
    suspend fun get(urlString: String, block: HttpRequestBuilder.() -> Unit): HttpResponse {
        return httpClient.get(urlString, block)
    }

    /**
     * Performs a GET request to a specified URL object with additional request configurations.
     * @param url The target URL as a Url object.
     * @param block The request configuration block.
     * @return The HttpResponse object.
     */
    suspend fun get(url: Url, block: HttpRequestBuilder.() -> Unit): HttpResponse {
        return httpClient.get(url, block)
    }

    /**
     * Performs a GET request with automatic deserialization of the response.
     * @param block The request configuration block.
     * @return The response body deserialized into type [T].
     */
    suspend inline fun <reified T> get(block: HttpRequestBuilder.() -> Unit): T {
        return httpClient.get(block).body<T>()
    }

    /**
     * Performs a GET request using an existing HttpRequestBuilder and deserializes the response.
     * @param builder The pre-configured request builder.
     * @return The response body deserialized into type [T].
     */
    suspend inline fun <reified T> get(builder: HttpRequestBuilder): T {
        return httpClient.get(builder = builder).body<T>()
    }

    /**
     * Performs a GET request to a specified URL string and deserializes the response.
     * @param urlString The target URL as a string.
     * @param block The request configuration block.
     * @return The response body deserialized into type [T].
     */
    suspend inline fun <reified T> get(urlString: String, block: HttpRequestBuilder.() -> Unit): T {
        return httpClient.get(urlString, block).body<T>()
    }

    /**
     * Performs a GET request to a specified URL object and deserializes the response.
     * @param url The target URL as a Url object.
     * @param block The request configuration block.
     * @return The response body deserialized into type [T].
     */
    suspend inline fun <reified T> get(url: Url, block: HttpRequestBuilder.() -> Unit): T {
        return httpClient.get(url, block).body<T>()
    }
}
