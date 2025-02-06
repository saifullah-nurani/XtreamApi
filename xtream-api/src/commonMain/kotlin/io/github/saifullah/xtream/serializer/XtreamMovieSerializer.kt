package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.ktx.added
import io.github.saifullah.xtream.ktx.casts
import io.github.saifullah.xtream.ktx.categoryId
import io.github.saifullah.xtream.ktx.categoryIds
import io.github.saifullah.xtream.ktx.containerExtension
import io.github.saifullah.xtream.ktx.crews
import io.github.saifullah.xtream.ktx.customSid
import io.github.saifullah.xtream.ktx.directSource
import io.github.saifullah.xtream.ktx.genres
import io.github.saifullah.xtream.ktx.longOrNull
import io.github.saifullah.xtream.ktx.num
import io.github.saifullah.xtream.ktx.plot
import io.github.saifullah.xtream.ktx.rating
import io.github.saifullah.xtream.ktx.rating5Based
import io.github.saifullah.xtream.ktx.releaseDate
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.streamIcon
import io.github.saifullah.xtream.ktx.streamType
import io.github.saifullah.xtream.ktx.title
import io.github.saifullah.xtream.ktx.tmdbId
import io.github.saifullah.xtream.ktx.youtubeTrailer
import io.github.saifullah.xtream.model.XtreamMovie
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

internal object XtreamMovieSerializer : KSerializer<XtreamMovie> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamMovie")

    override fun deserialize(decoder: Decoder): XtreamMovie {
        return try {
            if (decoder is JsonDecoder) {
                val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject
                XtreamMovie(
                    added = jsonObject.added,
                    casts = jsonObject.casts,
                    categoryId = jsonObject.categoryId,
                    categoryIds = jsonObject.categoryIds,
                    containerExtension = jsonObject.containerExtension,
                    customSid = jsonObject.customSid,
                    directSource = jsonObject.directSource,
                    crews = jsonObject.crews,
                    genres = jsonObject.genres,
                    num = jsonObject.num,
                    plot = jsonObject.plot,
                    rating = jsonObject.rating,
                    rating5based = jsonObject.rating5Based,
                    releaseDate = jsonObject.releaseDate,
                    streamIcon = jsonObject.streamIcon,
                    streamId = jsonObject["stream_id"]?.longOrNull()!!,
                    streamType = jsonObject.streamType,
                    title = jsonObject.title,
                    youtubeTrailer = jsonObject.youtubeTrailer,
                    tmdbId = jsonObject.tmdbId
                )
            } else decoder.decodeSerializableValue(serializer())
        } catch (e: SerializationException) {
            throw SerializationException("Error deserializing XtreamMovie", e)
        }
    }

    override fun serialize(encoder: Encoder, value: XtreamMovie) {
        encoder.encodeSerializableValue(serializer(), value)
    }
}