package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamEpisodeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamEpisodeSerializer::class)
data class XtreamEpisode(
    val added: Long?, // 1738672268
    @SerialName("container_extension") val containerExtension: String, // mkv
    @SerialName("custom_sid") val customSid: Int?,
    @SerialName("direct_source") val directSource: String?,
    @SerialName("episode_num") val episodeNum: Int, // 1
    val id: Long, // 9263103
    val season: Int, // 27
    val title: String, // The Voice - S27E01 - The Blind Auditions Season Premiere
    val info: Info,
) {
    data class Info(
        val bitrate: Int?, // 0
        @SerialName("cover_big") val coverBig: String?, // null
        val duration: String?, // 00:43:00
        @SerialName("duration_secs") val durationSecs: Int?, // 2580
        @SerialName("movie_image") val movieImage: String?, // null
        val plot: String?,
        val rating: Float?, // 0
        @SerialName("release_date") val releaseDate: String?, // 2025-02-03
        val season: Int?, // 27
        @SerialName("tmdb_id") val tmdbId: Int? // 5895807
    )
}