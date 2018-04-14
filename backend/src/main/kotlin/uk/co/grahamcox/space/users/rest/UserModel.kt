package uk.co.grahamcox.space.users.rest

import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.grahamcox.space.rest.hal.Meta

/**
 * Details of a User
 * @property links The links relevant to the user
 * @property meta The metadata about the user
 * @property email The email address of the user
 * @property displayName The display name of the user
 */
data class UserModel(
        @JsonProperty("_links") val links: UserLinks,
        @JsonProperty("_meta") val meta: Meta,
        val email: String,
        val displayName: String
)
