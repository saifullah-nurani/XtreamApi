package io.github.saifullah.xtream.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XtreamAuth(
    @SerialName("server_info") val serverInfo: ServerInfo,
    @SerialName("user_info") val userInfo: UserInfo
) {
    @Serializable
    data class ServerInfo(
        @SerialName("https_port") val httpsPort: String, // 443
        val port: Int, // 8985
        val revision: Int, // 2
        @SerialName("rtmp_port") val rtmpPort: String, // 8880
        @SerialName("server_protocol") val serverProtocol: String, // https
        @SerialName("time_now") val timeNow: String, // 2025-02-05 08:40:56
        @SerialName("timestamp_now") val timestampNow: Int, // 1738744856
        val timezone: String, // Europe/London
        val url: String, // example.com
        val version: String, // 1.5.12
        val xui: Boolean // true
    )

    @Serializable
    data class UserInfo(
        @SerialName("active_cons") val activeCons: String, // 0
        @SerialName("allowed_output_formats") val allowedOutputFormats: List<String>,
        val auth: Int, // 1
        @SerialName("created_at") val createdAt: String, // 1709026755
        @SerialName("exp_date") val expDate: String, // 1743068355
        @SerialName("is_trial") val isTrial: String, // 0
        @SerialName("max_connections") val maxConnections: String, // 1
        val message: String, // Welcome to xyz
        val password: String, //password
        val status: String, // Active
        val username: String // username
    )
}