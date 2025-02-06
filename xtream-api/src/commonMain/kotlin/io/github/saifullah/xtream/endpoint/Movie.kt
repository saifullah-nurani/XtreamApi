package io.github.saifullah.xtream.endpoint

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.github.saifullah.xtream.model.XtreamCategory
import io.github.saifullah.xtream.model.XtreamMovie
import io.github.saifullah.xtream.model.XtreamMovieDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Handles movie-related API calls for the Xtream platform.
 *
 * This class provides methods to fetch movies, movie details, categories,
 * and movies filtered by category.
 *
 * @property httpClient The HTTP client used for making network requests.
 */
class Movie internal constructor(override val httpClient: HttpClient) : XtreamApi {

    /**
     * Fetches a list of available movies.
     *
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     * If null, the default credentials will be used.
     *
     * @return A list of [XtreamMovie] representing the available movies.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getMovies(credentials: XtreamAuthCredentials? = null): List<XtreamMovie> {
        return httpClient.get {
            url(credentials, "get_vod_streams")
        }.body<List<XtreamMovie>>()
    }

    /**
     * Retrieves detailed information about a specific movie.
     *
     * @param streamId The unique ID of the movie stream.
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return [XtreamMovieDetail] containing detailed movie information.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getMovieDetail(
        streamId: Long,
        credentials: XtreamAuthCredentials? = null
    ): XtreamMovieDetail {
        return httpClient.get {
            url(credentials, action = "get_vod_info") {
                append("vod_id", "$streamId")
            }
        }.body<XtreamMovieDetail>()
    }

    /**
     * Fetches a list of movie categories available on the platform.
     *
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return A list of [XtreamCategory] representing available movie categories.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getMovieCategories(
        credentials: XtreamAuthCredentials? = null
    ): List<XtreamCategory> {
        return httpClient.get {
            url(credentials, action = "get_vod_categories")
        }.body<List<XtreamCategory>>()
    }

    /**
     * Fetches a list of movies filtered by category.
     *
     * @param categoryId The ID of the category to filter movies.
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return A list of [XtreamMovie] that belong to the specified category.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getMoviesByCategory(
        categoryId: Int,
        credentials: XtreamAuthCredentials? = null
    ): List<XtreamMovie> {
        return httpClient.get {
            url(credentials, action = "get_vod_streams") {
                append("category_id", "$categoryId")
            }
        }.body<List<XtreamMovie>>()
    }
}
