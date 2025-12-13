package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.ktx.backdropPaths
import io.github.saifullah.xtream.ktx.casts
import io.github.saifullah.xtream.ktx.categoryId
import io.github.saifullah.xtream.ktx.categoryIds
import io.github.saifullah.xtream.ktx.contentOrNull
import io.github.saifullah.xtream.ktx.cover
import io.github.saifullah.xtream.ktx.coverBig
import io.github.saifullah.xtream.ktx.crews
import io.github.saifullah.xtream.ktx.episodeRunTime
import io.github.saifullah.xtream.ktx.floatOrNull
import io.github.saifullah.xtream.ktx.genres
import io.github.saifullah.xtream.ktx.intOrNull
import io.github.saifullah.xtream.ktx.jsonArrayOrNull
import io.github.saifullah.xtream.ktx.jsonObjectOrNull
import io.github.saifullah.xtream.ktx.lastModified
import io.github.saifullah.xtream.ktx.plot
import io.github.saifullah.xtream.ktx.rating
import io.github.saifullah.xtream.ktx.rating5Based
import io.github.saifullah.xtream.ktx.releaseDate
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.title
import io.github.saifullah.xtream.ktx.tmdbId
import io.github.saifullah.xtream.ktx.youtubeTrailer
import io.github.saifullah.xtream.model.XtreamEpisode
import io.github.saifullah.xtream.model.XtreamTvSeriesDetail2
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

internal object XtreamTvSeriesDetail2Serializer : KSerializer<XtreamTvSeriesDetail2> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamTvSeriesDetail2")

    override fun deserialize(decoder: Decoder): XtreamTvSeriesDetail2 {
        if (decoder !is JsonDecoder)
            return decoder.decodeSerializableValue(serializer())

        try {
            val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject

            val seriesInfo = jsonObject["info"]
                ?.jsonObjectOrNull()
                ?.decodeSeriesInfo()

            val seasons = jsonObject["seasons"]
                ?.jsonArrayOrNull()
                ?.map { it.jsonObject.decodeSeasonInfo() }
                ?.takeIf { it.isNotEmpty() }

            val episodesMap: Map<Int, List<XtreamEpisode?>> =
                jsonObject["episodes"]?.let {
                    decoder.json.decodeFromJsonElement(
                        MapSerializer(
                            Int.serializer(),
                            ListSerializer(XtreamEpisodeSerializer)
                        ),
                        it
                    )
                } ?: emptyMap()

            val seasonsWithEpisodes =
                if (!seasons.isNullOrEmpty()) {
                    // Normal case
                    seasons.map { seasonInfo ->
                        XtreamTvSeriesDetail2.Season(
                            info = seasonInfo,
                            episodes = episodesMap[seasonInfo.seasonNumber].orEmpty()
                        )
                    }
                } else {
                    // 🔥 Xtream fallback (THIS FIXES YOUR ISSUE)
                    episodesMap.map { (seasonNumber, episodes) ->
                        XtreamTvSeriesDetail2.Season(
                            info = XtreamTvSeriesDetail2.Season.Info(
                                airDate = null,
                                cover = seriesInfo?.cover,
                                coverBig = seriesInfo?.cover,
                                episodeCount = episodes.size,
                                id = seasonNumber,
                                name = "Season $seasonNumber",
                                overview = null,
                                seasonNumber = seasonNumber,
                                voteAverage = null
                            ),
                            episodes = episodes.sortedBy { it?.episodeNum }
                        )
                    }.sortedBy { it.info?.seasonNumber }
                }

            return XtreamTvSeriesDetail2(
                info = seriesInfo,
                seasons = seasonsWithEpisodes
            )

        } catch (e: SerializationException) {
            throw e
        } catch (e: Exception) {
            throw SerializationException(
                "Error deserializing XtreamTvSeriesDetail2",
                e
            )
        }
    }


    override fun serialize(encoder: Encoder, value: XtreamTvSeriesDetail2) {
        encoder.encodeSerializableValue(serializer(), value)
    }

    private fun JsonObject.decodeSeriesInfo(): XtreamTvSeriesDetail2.Info {
        return XtreamTvSeriesDetail2.Info(
            backdropPaths = backdropPaths,
            casts = casts,
            categoryId = categoryId,
            categoryIds = categoryIds,
            cover = cover,
            crews = crews,
            episodeRunTime = episodeRunTime,
            genre = genres,
            lastModified = lastModified,
            plot = plot,
            rating = rating,
            rating5based = rating5Based,
            releaseDate = releaseDate,
            title = title,
            tmdbId = tmdbId,
            youtubeTrailer = youtubeTrailer
        )
    }

    private fun JsonObject.decodeSeasonInfo(): XtreamTvSeriesDetail2.Season.Info {
        return XtreamTvSeriesDetail2.Season.Info(
            airDate = this["air_date"]?.contentOrNull(),
            cover = cover,
            coverBig = coverBig,
            episodeCount = this["episode_count"]?.intOrNull() ?: 0,
            id = this["id"]?.intOrNull()
                ?: throw SerializationException("Missing required field: id in season info"),
            name = title,
            overview = this["overview"]?.contentOrNull(),
            seasonNumber = this["season_number"]?.intOrNull() ?: 0,
            voteAverage = this["vote_average"]?.floatOrNull()
        )
    }
}