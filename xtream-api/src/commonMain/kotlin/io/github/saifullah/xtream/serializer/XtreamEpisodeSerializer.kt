package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.model.XtreamEpisode
import io.github.saifullah.xtream.ktx.added
import io.github.saifullah.xtream.ktx.bitrate
import io.github.saifullah.xtream.ktx.containerExtension
import io.github.saifullah.xtream.ktx.coverBig
import io.github.saifullah.xtream.ktx.customSid
import io.github.saifullah.xtream.ktx.directSource
import io.github.saifullah.xtream.ktx.duration
import io.github.saifullah.xtream.ktx.durationInSecond
import io.github.saifullah.xtream.ktx.intOrNull
import io.github.saifullah.xtream.ktx.longOrNull
import io.github.saifullah.xtream.ktx.movieImage
import io.github.saifullah.xtream.ktx.plot
import io.github.saifullah.xtream.ktx.rating
import io.github.saifullah.xtream.ktx.releaseDate
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.title
import io.github.saifullah.xtream.ktx.tmdbId
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

object XtreamEpisodeSerializer : KSerializer<XtreamEpisode> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamEpisode")

    override fun deserialize(decoder: Decoder): XtreamEpisode {
        return try {
            if (decoder is JsonDecoder) {
                val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject
                val infoJsonObject = jsonObject["info"]?.jsonObject
                XtreamEpisode(
                    added = jsonObject.added,
                    containerExtension = jsonObject.containerExtension!!,
                    customSid = jsonObject.customSid,
                    directSource = jsonObject.directSource,
                    episodeNum = jsonObject["episode_num"]?.intOrNull() ?: 0,
                    id = jsonObject["id"]?.longOrNull()!!,
                    season = jsonObject["season"]?.intOrNull() ?: 0,
                    title = jsonObject.title,
                    info = XtreamEpisode.Info(
                        bitrate = infoJsonObject?.bitrate,
                        coverBig = infoJsonObject?.coverBig,
                        duration = infoJsonObject?.duration,
                        durationSecs = infoJsonObject?.durationInSecond,
                        movieImage = infoJsonObject?.movieImage,
                        plot = infoJsonObject?.plot,
                        rating = infoJsonObject?.rating,
                        releaseDate = infoJsonObject?.releaseDate,
                        season = infoJsonObject?.get("season")?.intOrNull(),
                        tmdbId = infoJsonObject?.tmdbId
                    ),
                )
            } else decoder.decodeSerializableValue(serializer())
        } catch (e: SerializationException) {
            throw SerializationException("Error deserializing XtreamEpisode", e)
        }
    }

    override fun serialize(encoder: Encoder, value: XtreamEpisode) {
        encoder.encodeSerializableValue(serializer(), value)
    }
}