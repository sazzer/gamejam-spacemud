package uk.co.grahamcox.space.authorization.rest

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * JSON Representation of an Access Token
 * @property accessToken The Serialized form of the access token
 * @property expires The number of seconds the access token is still valid for
 */
data class AccessTokenModel(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("expires_in") val expires: Long
) {
    @JsonProperty("token_type")
    val tokenType = "Bearer"
}
