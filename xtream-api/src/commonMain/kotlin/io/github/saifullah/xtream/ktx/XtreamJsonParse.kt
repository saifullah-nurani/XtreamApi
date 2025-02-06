package io.github.saifullah.xtream.ktx

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject

internal val JsonObject.added: Long?
    get() = this["added"]?.longOrNull()

internal val JsonObject.containerExtension: String?
    get() = this["container_extension"]?.contentOrNull()

internal val JsonObject.directSource: String?
    get() = this["direct_source"]?.contentOrNull()

internal val JsonObject.customSid: Int?
    get() = this["custom_sid"]?.intOrNull()

internal val JsonObject.title: String
    get() = this["title"]?.contentOrNull() ?: this["name"]?.contentOrNull()!!

internal val JsonObject.bitrate: Int?
    get() = this["bitrate"]?.intOrNull()

internal val JsonObject.coverBig: String?
    get() = this["cover_big"]?.contentOrNull()

internal val JsonObject.durationInSecond: Int?
    get() = this["duration"]?.intOrNull()

internal val JsonObject.duration: String?
    get() = this["duration"]?.contentOrNull()

internal val JsonObject.movieImage: String?
    get() = this["movie_image"]?.contentOrNull()

internal val JsonObject.plot: String?
    get() = this["plot"]?.contentOrNull() ?: this["description"]?.contentOrNull()

internal val JsonObject.rating: Float?
    get() = this["rating"]?.floatOrNull()

internal val JsonObject.rating5Based: Float?
    get() = this["rating_5based"]?.floatOrNull()

internal val JsonObject.releaseDate: String?
    get() = this["release_date"]?.contentOrNull()

internal val JsonObject.tmdbId: Int?
    get() = this["tmdb_id"]?.intOrNull() ?: this["tmdb"]?.intOrNull()

internal val JsonObject.backdropPaths: List<String>?
    get() = this["backdrop_path"]?.parseToList<String>() ?: this["backdrops"]?.parseToList<String>()

internal val JsonObject.categoryId: Int?
    get() = this["category_id"]?.intOrNull()

internal val JsonObject.categoryIds: List<Int>?
    get() = this["category_ids"]?.parseToList<Int>()

internal val JsonObject.cover: String?
    get() = this["cover"]?.contentOrNull()

internal val JsonObject.crews: List<String>?
    get() = this["director"]?.parseToList<String>()

internal val JsonObject.episodeRunTime: Int?
    get() = this["episode_run_time"]?.intOrNull()

internal val JsonObject.genres: List<String>?
    get() = this["genre"]?.parseToList<String>()

internal val JsonObject.lastModified: Long?
    get() = this["last_modified"]?.longOrNull()

internal val JsonObject.youtubeTrailer: String?
    get() = this["youtube_trailer"]?.contentOrNull() ?: this["trailer"]?.contentOrNull()

internal val JsonObject.casts: List<String>?
    get() = this["cast"]?.parseToList<String>() ?: this["actors"]?.parseToList<String>()

internal val JsonObject.num: Int
    get() = this["num"]!!.intOrNull()  ?: throw SerializationException("Missing required field 'num'")

internal val JsonObject.streamIcon: String?
    get() = this["stream_icon"]?.contentOrNull()

internal val JsonObject.streamType: String
    get() = this["stream_type"]?.contentOrNull()
        ?: throw SerializationException("Missing required field 'stream_type'")

