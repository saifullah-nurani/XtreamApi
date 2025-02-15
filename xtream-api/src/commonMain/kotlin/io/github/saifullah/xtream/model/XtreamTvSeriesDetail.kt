package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamTvSeriesDetailInfoSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XtreamTvSeriesDetail(
    val episodes: Map<Int, List<XtreamEpisode>>,
    val info: Info,
    val seasons: List<XtreamSeason>
) {
    @Serializable(with = XtreamTvSeriesDetailInfoSerializer::class)
    data class Info(
        @SerialName("backdrop_path") val backdropPaths: List<String>?,
        val casts: List<String>?, // Carson Daly, Adam Levine, John Legend, Michael Bublé, Kelsea Ballerini
        @SerialName("category_id") val categoryId: Int?, // 272
        @SerialName("category_ids") val categoryIds: List<Int>?,
        val cover: String?, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/8jgjykcCuQ5rYreyqba4mazNKYd.jpg
        val crews: List<String>?,
        @SerialName("episode_run_time") val episodeRunTime: Int?, // 43
        val genre: List<String>?,
        @SerialName("last_modified") val lastModified: Long?, // 1738672268
        val plot: String?, // The strongest vocalists from across the United states compete in a blockbusters vocal competition, the winner becomes “The Voice.” The show's innovative format features four stages of competition: the blind auditions, the battle rounds, the knockouts and, finally, the live performance shows.
        val rating: Float?, // 6
        @SerialName("rating_5based") val rating5based: Float?, // 3
        @SerialName("release_date") val releaseDate: String?, // 2011-04-26
        val title: String, // The Voice,
        @SerialName("tmdb_id")
        val tmdbId:Int?,
        @SerialName("youtube_trailer") val youtubeTrailer: String?
    )
}