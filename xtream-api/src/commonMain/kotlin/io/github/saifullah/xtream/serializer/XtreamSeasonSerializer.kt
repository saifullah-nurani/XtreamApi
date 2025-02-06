package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.model.XtreamSeason
import io.github.saifullah.xtream.ktx.contentOrNull
import io.github.saifullah.xtream.ktx.cover
import io.github.saifullah.xtream.ktx.coverBig
import io.github.saifullah.xtream.ktx.floatOrNull
import io.github.saifullah.xtream.ktx.intOrNull
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.title
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

object XtreamSeasonSerializer : KSerializer<XtreamSeason> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamSeason")

    override fun deserialize(decoder: Decoder): XtreamSeason {
        return try {
            if (decoder is JsonDecoder) {
                val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject
                XtreamSeason(
                    airDate = jsonObject["air_date"]?.contentOrNull(),
                    cover = jsonObject.cover,
                    coverBig = jsonObject.coverBig,
                    episodeCount = jsonObject["episode_count"]?.intOrNull() ?: 0,
                    id = jsonObject["id"]?.intOrNull()!!,
                    name = jsonObject.title,
                    overview = jsonObject["overview"]?.contentOrNull(),
                    seasonNumber = jsonObject["season_number"]?.intOrNull() ?: 0,
                    voteAverage = jsonObject["vote_average"]?.floatOrNull()
                )
            } else decoder.decodeSerializableValue(serializer())
        } catch (e: SerializationException) {
            throw SerializationException("Error deserializing XtreamSeason", e)
        }
    }

    override fun serialize(encoder: Encoder, value: XtreamSeason) {
        encoder.encodeSerializableValue(serializer(), value)
    }
}