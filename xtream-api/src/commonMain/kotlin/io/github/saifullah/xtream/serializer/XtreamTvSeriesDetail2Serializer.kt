package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.model.XtreamTvSeriesDetail2
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
import io.github.saifullah.xtream.ktx.lastModified
import io.github.saifullah.xtream.ktx.plot
import io.github.saifullah.xtream.ktx.rating
import io.github.saifullah.xtream.ktx.rating5Based
import io.github.saifullah.xtream.ktx.releaseDate
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.title
import io.github.saifullah.xtream.ktx.youtubeTrailer
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
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

internal object XtreamTvSeriesDetail2Serializer : KSerializer<XtreamTvSeriesDetail2> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamTvSeriesDetail")

    override fun deserialize(decoder: Decoder): XtreamTvSeriesDetail2 {
        return try {
            if (decoder is JsonDecoder) {
                val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject
                val seriesInfo = jsonObject["info"]!!.jsonObject.decodeSeriesInfo()
                val seasons = jsonObject["seasons"]!!.jsonArray.map { it.jsonObject.decodeSeasonInfo() }
                val episodesMap = jsonObject["episodes"]!!.let {
                    decoder.json.decodeFromJsonElement(
                        MapSerializer(Int.serializer(), ListSerializer(XtreamEpisodeSerializer)), it
                    )
                }
                val seasonsWithEpisodes = seasons.map { seasonInfo ->
                    val seasonNumber = seasonInfo.seasonNumber
                    val episodes = episodesMap[seasonNumber] ?: emptyList()
                    XtreamTvSeriesDetail2.Season(info = seasonInfo, episodes = episodes)
                }

                XtreamTvSeriesDetail2(info = seriesInfo, seasons = seasonsWithEpisodes)
            } else
                decoder.decodeSerializableValue(serializer())
        } catch (e: Exception) {
            throw SerializationException("Error deserializing XtreamMovie", e)
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
            youtubeTrailer = youtubeTrailer
        )
    }

    private fun JsonObject.decodeSeasonInfo(): XtreamTvSeriesDetail2.Season.Info {
        return XtreamTvSeriesDetail2.Season.Info(
            airDate = this["air_date"]?.contentOrNull(),
            cover = cover,
            coverBig = coverBig,
            episodeCount = this["episode_count"]?.intOrNull() ?: 0,
            id = this["id"]?.intOrNull()!!,
            name = title,
            overview = this["overview"]?.contentOrNull(),
            seasonNumber = this["season_number"]?.intOrNull() ?: 0,
            voteAverage = this["vote_average"]?.floatOrNull()
        )
    }
}