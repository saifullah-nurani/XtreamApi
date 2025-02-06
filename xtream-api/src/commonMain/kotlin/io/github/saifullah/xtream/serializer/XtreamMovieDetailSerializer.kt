package io.github.saifullah.xtream.serializer

import io.github.saifullah.xtream.ktx.added
import io.github.saifullah.xtream.ktx.backdropPaths
import io.github.saifullah.xtream.ktx.bitrate
import io.github.saifullah.xtream.ktx.casts
import io.github.saifullah.xtream.ktx.categoryId
import io.github.saifullah.xtream.ktx.categoryIds
import io.github.saifullah.xtream.ktx.containerExtension
import io.github.saifullah.xtream.ktx.contentOrNull
import io.github.saifullah.xtream.ktx.coverBig
import io.github.saifullah.xtream.ktx.crews
import io.github.saifullah.xtream.ktx.customSid
import io.github.saifullah.xtream.ktx.directSource
import io.github.saifullah.xtream.ktx.duration
import io.github.saifullah.xtream.ktx.durationInSecond
import io.github.saifullah.xtream.ktx.genres
import io.github.saifullah.xtream.ktx.longOrNull
import io.github.saifullah.xtream.ktx.movieImage
import io.github.saifullah.xtream.ktx.plot
import io.github.saifullah.xtream.ktx.rating
import io.github.saifullah.xtream.ktx.releaseDate
import io.github.saifullah.xtream.ktx.safeJsonDecoder
import io.github.saifullah.xtream.ktx.title
import io.github.saifullah.xtream.ktx.tmdbId
import io.github.saifullah.xtream.ktx.youtubeTrailer
import io.github.saifullah.xtream.model.XtreamMovieDetail
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

object XtreamMovieDetailSerializer : KSerializer<XtreamMovieDetail> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("XtreamMovieDetail")

    override fun deserialize(decoder: Decoder): XtreamMovieDetail {
        return try {
            if (decoder is JsonDecoder) {
                val jsonObject = decoder.safeJsonDecoder().decodeJsonElement().jsonObject
                val info = jsonObject["info"]?.jsonObject!!
                val movieData = jsonObject["movie_data"]?.jsonObject!!
                XtreamMovieDetail(
                    info = XtreamMovieDetail.Info(
                        casts = info.casts,
                        age = info["age"]?.contentOrNull(),
                        backdropPath = info.backdropPaths,
                        bitrate = info.bitrate,
                        country = info["country"]?.contentOrNull(),
                        coverBig = info.coverBig,
                        description = info.plot,
                        crews = info.crews,
                        duration = info.duration,
                        durationSecs = info.durationInSecond,
                        genres = info.genres,
                        movieImage = info.movieImage,
                        name = info.title,
                        rating = info.rating,
                        releaseDate = info.releaseDate,
                        tmdbId = info.tmdbId,
                        youtubeTrailer = info.youtubeTrailer
                    ),
                    movieData = XtreamMovieDetail.MovieData(
                        added = movieData.added,
                        categoryId = movieData.categoryId,
                        categoryIds = movieData.categoryIds,
                        containerExtension = movieData.containerExtension!!,
                        customSid = movieData.customSid,
                        directSource = movieData.directSource,
                        streamId = movieData["stream_id"]?.longOrNull()!!,
                        title = movieData.title
                    )
                )
            } else decoder.decodeSerializableValue(serializer())
        } catch (e: SerializationException) {
            throw SerializationException("Error deserializing XtreamMovieDetail", e)
        }
    }

    override fun serialize(encoder: Encoder, value: XtreamMovieDetail) {
        encoder.encodeSerializableValue(serializer(), value)
    }
}