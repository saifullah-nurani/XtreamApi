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
    internal constructor() : this("http", 0, "", "", "")
    
    /**
     * Validates that all required credentials are provided.
     * @return true if credentials are valid, false otherwise
     */
    fun isValid(): Boolean {
        return host.isNotBlank() && 
               username.isNotBlank() && 
               password.isNotBlank() && 
               port > 0
    }
}
