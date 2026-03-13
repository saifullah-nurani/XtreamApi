package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamTvSeriesDetail2Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamTvSeriesDetail2Serializer::class)
data class XtreamTvSeriesDetail2(
    val info: Info? = null,
    val seasons: List<Season> = emptyList()
) {
    @Serializable
    data class Info(
        @SerialName("backdrop_path") val backdropPaths: List<String>? = null,
        val casts: List<String>? = null, // Carson Daly, Adam Levine, John Legend, Michael Bublé, Kelsea Ballerini
        @SerialName("category_id") val categoryId: Int? = null, // 272
        @SerialName("category_ids") val categoryIds: List<Int>? = null,
        val cover: String? = null, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/8jgjykcCuQ5rYreyqba4mazNKYd.jpg
        val crews: List<String>? = null,
        @SerialName("episode_run_time") val episodeRunTime: Int? = null, // 43
        val genre: List<String>? = null,
        @SerialName("last_modified") val lastModified: Long? = null, // 1738672268
        val plot: String? = null, // The strongest vocalists from across the United states compete in a blockbusters vocal competition, the winner becomes “The Voice.” The show's innovative format features four stages of competition: the blind auditions, the battle rounds, the knockouts and, finally, the live performance shows.
        val rating: Float? = null, // 6
        @SerialName("rating_5based") val rating5based: Float? = null, // 3
        @SerialName("release_date") val releaseDate: String? = null, // 2011-04-26
        @SerialName("tmdb_id")
        val tmdbId: Int? = null, // 8771496
        val title: String = "", // The Voice
        @SerialName("youtube_trailer") val youtubeTrailer: String? = null
    )

    @Serializable
    data class Season(
        val info: Info? = null,
        val episodes: List<XtreamEpisode?> = emptyList()
    ) {
        @Serializable
        data class Info(
            @SerialName("air_date") val airDate: String? = null, // 2011-04-21
            val cover: String? = null, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/lsXbfael8KrSC2N0Q5MmTAcJJao.jpg
            @SerialName("cover_big") val coverBig: String? = null, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/lsXbfael8KrSC2N0Q5MmTAcJJao.jpg
            @SerialName("episode_count") val episodeCount: Int = 0, // 3
            val id: Int = 0, // 49011
            val name: String? = null, // Specials
            val overview: String? = null,
            @SerialName("season_number") val seasonNumber: Int = 0, // 0
            @SerialName("vote_average") val voteAverage: Float? = null
        )
    }
}