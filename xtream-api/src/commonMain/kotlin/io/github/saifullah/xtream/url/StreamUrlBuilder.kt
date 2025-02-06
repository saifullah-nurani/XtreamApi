package io.github.saifullah.xtream.url

import io.github.saifullah.xtream.model.XtreamAuthCredentials

object StreamUrlBuilder {
    /**
     * Constructs a URL for streaming a movie.
     *
     * @param protocol The protocol to use (e.g., "http", "https")
     * @param host The host domain or IP address.
     * @param username The Xtream username for authentication.
     * @param password The Xtream password for authentication.
     * @param streamId The unique identifier for the movie stream.
     * @param extension The file extension (e.g., "mp4", "mkv").
     *
     * @return A formatted movie streaming URL.
     */
    fun buildMovieUrl(
        protocol: String,
        host: String,
        username: String,
        password: String,
        streamId: Long,
        extension: String
    ): String {
        return "${
            protocol.replace(
                "//|/|:".toRegex(),
                ""
            )
        }://$host/movie/$username/$password/$streamId.$extension"
    }

    /**
     * Constructs a URL for streaming a TV episode.
     *
     * @param protocol The protocol to use (e.g., "http", "https")
     * @param host The host domain or IP address.
     * @param username The Xtream username for authentication.
     * @param password The Xtream password for authentication.
     * @param episodeId The unique identifier for the TV episode stream.
     * @param extension The file extension (e.g., "mp4", "mkv").
     *
     * @return A formatted TV episode streaming URL.
     */
    fun buildEpisodeUrl(
        protocol: String,
        host: String,
        username: String,
        password: String,
        episodeId: Long,
        extension: String
    ): String {
        return "${
            protocol.replace(
                "//|/|:".toRegex(),
                ""
            )
        }://$host/series/$username/$password/$episodeId.$extension"
    }

    /**
     * Constructs a movie streaming URL using Xtream authentication credentials.
     *
     * @param authCredentials The authentication credentials containing protocol, host, username, and password.
     * @param streamId The unique identifier for the movie stream.
     * @param extension The file extension (e.g., "mp4", "mkv").
     *
     * @return A formatted movie streaming URL.
     */
    fun buildMovieUrl(
        authCredentials: XtreamAuthCredentials,
        streamId: Long,
        extension: String
    ): String {
        return buildMovieUrl(
            protocol = authCredentials.protocol,
            host = authCredentials.host,
            username = authCredentials.username,
            password = authCredentials.password,
            streamId = streamId,
            extension = extension
        )
    }

    /**
     * Constructs a TV episode streaming URL using Xtream authentication credentials.
     *
     * @param authCredentials The authentication credentials containing protocol, host, username, and password.
     * @param episodeId The unique identifier for the TV episode stream.
     * @param extension The file extension (e.g., "mp4", "mkv").
     *
     * @return A formatted TV episode streaming URL.
     */
    fun buildEpisodeUrl(
        authCredentials: XtreamAuthCredentials,
        episodeId: Long,
        extension: String
    ): String {
        return buildEpisodeUrl(
            protocol = authCredentials.protocol,
            host = authCredentials.host,
            username = authCredentials.username,
            password = authCredentials.password,
            episodeId = episodeId,
            extension = extension
        )
    }

}