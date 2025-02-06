package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamTvSeriesSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamTvSeriesSerializer::class)
data class XtreamTvSeries(
    @SerialName("backdrops") val backdrops: List<String>?,
    @SerialName("casts")
    val casts: List<String>?, // Toby Stephens, Luke Arnold, Hannah New, Jessica Parker Kennedy, Toby Schmitz
    @SerialName("category_id") val categoryId: Int?, // 261
    @SerialName("category_ids") val categoryIds: List<Int>?,
    val cover: String?, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/mZcSwrDdw6cdOVgXm496DgwrQcQ.jpg
    @SerialName("crews")
    val crews: List<String>?,
    @SerialName("genres")
    val genres: List<String>?, // Drama, Action & Adventure
    @SerialName("last_modified") val lastModified: Long?, // 17381508
    val num: Int, // 1
    val plot: String?, // The pirate adventures of Captain Flint and his men twenty years prior to Robert Louis Stevenson’s classic “Treasure Island.” Flint, the most brilliant and most feared pirate captain of his day, takes on a fast-talking young addition to his crew who goes by the name John Silver. Threatened with extinction on all sides, they fight for the survival of New Providence Island, the most notorious criminal haven of its day – a debauched paradise teeming with pirates, prostitutes, thieves and fortune seekers, a place defined by both its enlightened ideals and its stunning brutality.
    val rating: Float?, // 8
    @SerialName("rating_5based") val rating5based: Float?, // 3.5
    @SerialName("release_date") val releaseDate: String?, // 2014-01-25
    @SerialName("series_id") val seriesId: Long, // 1
    @SerialName("stream_type") val streamType: String, // series
    val title: String, // Black Sails
    @SerialName("youtube_trailer") val youtubeTrailer: String? // rT2Y5jjBNpQ
)