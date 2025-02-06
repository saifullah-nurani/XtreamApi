package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.serializer.XtreamSeasonSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = XtreamSeasonSerializer::class)
data class XtreamSeason(
    @SerialName("air_date") val airDate: String?, // 2011-04-21
    val cover: String?, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/lsXbfael8KrSC2N0Q5MmTAcJJao.jpg
    @SerialName("cover_big") val coverBig: String?, // https://image.tmdb.org/t/p/w600_and_h900_bestv2/lsXbfael8KrSC2N0Q5MmTAcJJao.jpg
    @SerialName("episode_count") val episodeCount: Int, // 3
    val id: Int, // 49011
    val name: String, // Specials
    val overview: String?,
    @SerialName("season_number") val seasonNumber: Int, // 0
    @SerialName("vote_average") val voteAverage: Float? // 7.3
)
