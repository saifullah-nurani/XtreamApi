package io.github.saifullah.xtream.model

import io.github.saifullah.xtream.XtreamDsl
import kotlinx.serialization.Serializable

@Serializable
@XtreamDsl
data class XtreamAuthCredentials(
    var protocol: String,
    var port: Int,
    var host: String,
    var username: String,
    var password: String
) {
    internal constructor() : this("https", 0, "", "", "")
}
