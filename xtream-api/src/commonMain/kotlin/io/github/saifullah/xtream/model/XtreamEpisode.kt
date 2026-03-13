package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamEpisodeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamEpisodeSerializer::class)
data class XtreamEpisode(
    val added: Long? = null, // 1738672268
    @SerialName("container_extension") val containerExtension: String? = null, // mkv
    @SerialName("custom_sid") val customSid: Int? = null,
    @SerialName("direct_source") val directSource: String? = null,
    @SerialName("episode_num") val episodeNum: Int = 0, // 1
    val id: Long = 0L, // 9263103
    val season: Int = 0, // 27
    val title: String? = null, // The Voice - S27E01 - The Blind Auditions Season Premiere
    val info: Info? = null,
) {
    data class Info(
        val bitrate: Int? = null, // 0
        @SerialName("cover_big") val coverBig: String? = null, // null
        val duration: String? = null, // 00:43:00
        @SerialName("duration_secs") val durationSecs: Int? = null, // 2580
        @SerialName("movie_image") val movieImage: String? = null, // null
        val plot: String? = null,
        val rating: Float? = null, // 0
        @SerialName("release_date") val releaseDate: String? = null, // 2025-02-03
        val season: Int? = null, // 27
        @SerialName("tmdb_id") val tmdbId: Int? // 5895807 = 0
    )
}