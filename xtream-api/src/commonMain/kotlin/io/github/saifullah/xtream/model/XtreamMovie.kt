package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.core.StreamType
import io.github.saifullah.xtream.serializer.XtreamMovieSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamMovieSerializer::class)
data class XtreamMovie(
    val added: Long? = null, // 173087706
    val casts: List<String>? = null, // Marthino Lio, Ladya Cheryl, Reza Rahadian, Ratu Felisha, Sal Priadi
    @SerialName("category_id") val categoryId: Int? = null, // 478
    @SerialName("category_ids") val categoryIds: List<Int>? = null,
    @SerialName("container_extension") val containerExtension: String? = null, // mkv
    @SerialName("custom_sid") val customSid: Int? = null,
    @SerialName("direct_source") val directSource: String? = null,
    val crews: List<String>? = null, // Agung Sipapaga, Hendi Irnandi, Gadis Fajriani, Edwin
    val genres: List<String>? = null, // Action, Romance, Comedy
    val num: Int = 0, // 1
    val plot: String? = null, // Ajo Kawir is a fighter who fears nothing, not even death. His raging urge to fight is driven by a secret: his impotence. When he crosses paths with a tough female fighter named Iteung, Ajo gets beaten black and blue, but he also falls head over heels in love. Will Ajo’s path lead him to a happy life with Iteung, and, eventually, his own peace of mind?
    val rating: Float? = null, // 6.2
    @SerialName("rating_5based") val rating5based: Float? = null, // 3.1
    @SerialName("release_date") val releaseDate: String? = null, // 2022-01-28
    @SerialName("stream_icon") val streamIcon: String? = null, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/wtUaJFqFZMFjgN7KM0ikIFiemYB.jpg
    @SerialName("stream_id") val streamId: Long = 0L, // 8771496
    val streamType: StreamType, // movie
    val title: String = "", // Seperti Dendam, Rindu Harus Dibayar Tuntas - 2021
    @SerialName("youtube_trailer") val youtubeTrailer: String? = null, // wC_tvlc_KgU
    @SerialName("tmdb_id") val tmdbId: Int? = null
)