package io.github.saifullah.xtream.endpoint

import io.github.saifullah.xtream.model.XtreamAuthCredentials
import io.github.saifullah.xtream.model.XtreamCategory
import io.github.saifullah.xtream.model.XtreamTvSeries
import io.github.saifullah.xtream.model.XtreamTvSeriesDetail
import io.github.saifullah.xtream.model.XtreamTvSeriesDetail2
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Handles TV series-related API calls for the Xtream platform.
 *
 * This class provides methods to fetch TV series, TV series details, categories,
 * and series filtered by category.
 *
 * @property httpClient The HTTP client used for making network requests.
 */
class TvSeries internal constructor(
    override val httpClient: HttpClient
) : XtreamApi {

    /**
     * Fetches a list of available TV series.
     *
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     * If null, the default credentials will be used.
     *
     * @return A list of [XtreamTvSeries] representing the available TV series.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getTvSeries(credentials: XtreamAuthCredentials? = null): List<XtreamTvSeries> {
        return httpClient.get {
            url(credentials, action = "get_series")
        }.body<List<XtreamTvSeries>>()
    }

    /**
     * Retrieves detailed information about a specific TV series.
     *
     * @param seriesId The unique ID of the TV series.
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return [XtreamTvSeriesDetail] containing detailed TV series information.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getTvSeriesDetail(
        seriesId: Long, credentials: XtreamAuthCredentials? = null
    ): XtreamTvSeriesDetail {
        return httpClient.get {
            url(credentials, action = "get_series_info") {
                append("series_id", "$seriesId")
            }
        }.body<XtreamTvSeriesDetail>()
    }

    /**
     * Retrieves additional detailed information about a specific TV series.
     *
     * This method is similar to [getTvSeriesDetail] but returns a different data model.
     *
     * @param seriesId The unique ID of the TV series.
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return [XtreamTvSeriesDetail2] containing additional TV series details.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getTvSeriesDetail2(
        seriesId: Long, credentials: XtreamAuthCredentials? = null
    ): XtreamTvSeriesDetail2 {
        return httpClient.get {
            url(credentials, action = "get_series_info") {
                append("series_id", "$seriesId")
            }
        }.body<XtreamTvSeriesDetail2>()
    }

    /**
     * Fetches a list of TV series categories available on the platform.
     *
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return A list of [XtreamCategory] representing available TV series categories.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getTvSeriesCategories(
        credentials: XtreamAuthCredentials? = null
    ): List<XtreamCategory> {
        return httpClient.get {
            url(credentials, action = "get_series_categories")
        }.body<List<XtreamCategory>>()
    }

    /**
     * Fetches a list of TV series filtered by category.
     *
     * @param categoryId The ID of the category to filter TV series.
     * @param credentials Optional [XtreamAuthCredentials] for authentication.
     *
     * @return A list of [XtreamTvSeries] that belong to the specified category.
     *
     * @throws ClientRequestException If the request fails due to an HTTP error.
     * @throws SerializationException If the response cannot be parsed.
     */
    suspend fun getTvSeriesByCategory(
        categoryId: Int, credentials: XtreamAuthCredentials? = null
    ): List<XtreamTvSeries> {
        return httpClient.get {
            url(credentials, action = "get_series") {
                append("category_id", "$categoryId")
            }
        }.body<List<XtreamTvSeries>>()
    }
}
