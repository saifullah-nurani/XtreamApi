package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.model.XtreamTvSeries
import io.github.saifullah.xtream.ktx.backdropPaths
import io.github.saifullah.xtream.ktx.casts
import io.github.saifullah.xtream.ktx.categoryId
import io.github.saifullah.xtream.ktx.categoryIds
import io.github.saifullah.xtream.ktx.cover
import io.github.saifullah.xtream.ktx.crews
import io.github.saifullah.xtream.ktx.genres
import io.github.saifullah.xtream.ktx.lastModified
import io.github.saifullah.xtream.ktx.longOrNull
import io.github.saifullah.xtream.ktx.num
import io.github.saifullah.xtream.ktx.plot
import io.github.saifullah.xtream.ktx.rating
import io.github.saifullah.xtream.ktx.rating5Based
import io.github.saifullah.xtream.ktx.releaseDate
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.streamType
import io.github.saifullah.xtream.ktx.title
import io.github.saifullah.xtream.ktx.youtubeTrailer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

internal object XtreamTvSeriesSerializer : KSerializer<XtreamTvSeries> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamTvSeries")

    override fun deserialize(decoder: Decoder): XtreamTvSeries {
        return try {
            if (decoder is JsonDecoder) {
                val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject
                XtreamTvSeries(
                    backdrops = jsonObject.backdropPaths,
                    casts = jsonObject.casts,
                    categoryId = jsonObject.categoryId,
                    categoryIds = jsonObject.categoryIds,
                    cover = jsonObject.cover,
                    crews = jsonObject.crews,
                    genres = jsonObject.genres,
                    lastModified = jsonObject.lastModified,
                    num = jsonObject.num,
                    plot = jsonObject.plot,
                    rating = jsonObject.rating,
                    rating5based = jsonObject.rating5Based,
                    releaseDate = jsonObject.releaseDate,
                    seriesId = jsonObject["series_id"]?.longOrNull() ?: 0,
                    streamType = jsonObject.streamType,
                    title = jsonObject.title,
                    youtubeTrailer = jsonObject.youtubeTrailer
                )
            } else {
                decoder.decodeSerializableValue(serializer())
            }
        } catch (e: SerializationException) {
            throw SerializationException("Error deserializing XtreamTvSeries", e)
        }
    }

    override fun serialize(encoder: Encoder, value: XtreamTvSeries) {
        encoder.encodeSerializableValue(serializer(), value)
    }
}