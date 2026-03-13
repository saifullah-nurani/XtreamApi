package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamMovieDetailSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamMovieDetailSerializer::class)
data class XtreamMovieDetail(
    val info: Info,
    @SerialName("movie_data") val movieData: MovieData
) {
    @Serializable
    data class Info(
        val casts: List<String>? = null, // Marthino Lio, Ladya Cheryl, Reza Rahadian, Ratu Felisha, Sal Priadi
        val age: String? = null,
        @SerialName("backdrop_path") val backdropPath: List<String>? = null,
        val bitrate: Int? = null, // 1104
        val country: String? = null, // Germany
        @SerialName("cover_big") val coverBig: String? = null, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/wtUaJFqFZMFjgN7KM0ikIFiemYB.jpg
        val description: String? = null, // Ajo Kawir is a fighter who fears nothing, not even death. His raging urge to fight is driven by a secret: his impotence. When he crosses paths with a tough female fighter named Iteung, Ajo gets beaten black and blue, but he also falls head over heels in love. Will Ajo’s path lead him to a happy life with Iteung, and, eventually, his own peace of mind?
        val crews: List<String>? = null, // Agung Sipapaga, Hendi Irnandi, Gadis Fajriani, Edwin
        val duration: String? = null, // 01:54:36
        @SerialName("duration_secs") val durationSecs: Int? = null, // 6876
        val genres: List<String>? = null, // Action, Romance, Comedy
        @SerialName("movie_image") val movieImage: String? = null, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/wtUaJFqFZMFjgN7KM0ikIFiemYB.jpg
        val name: String = "", // Vengeance Is Mine, All Others Pay Cash
        val rating: Float? = null, // 6.2
        @SerialName("releasedate")
        val releaseDate: String? = null, // null
        @SerialName("tmdb_id") val tmdbId: Int? = null, // 674255
        @SerialName("youtube_trailer") val youtubeTrailer: String? // wC_tvlc_KgU = ""
    )

    @Serializable
    data class MovieData(
        val added: Long? = null, // 1730877061
        @SerialName("category_id") val categoryId: Int? = null, // 478
        @SerialName("category_ids") val categoryIds: List<Int>? = null,
        @SerialName("container_extension") val containerExtension: String? = null, // mkv
        @SerialName("custom_sid") val customSid: Int? = null,
        @SerialName("direct_source") val directSource: String? = null,
        @SerialName("stream_id") val streamId: Long = 0L, // 8771496
        val title: String = "", // Seperti Dendam, Rindu Harus Dibayar Tuntas - 2021
    )
}