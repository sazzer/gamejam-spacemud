package uk.co.grahamcox.space.users.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped
import uk.co.grahamcox.space.authorization.rest.AccessTokenModel

/**
 * Details of resources embedded in an Authorized User Model
 * @property token The users access token
 */
data class AuthorizedUserEmbedded(
        val token: AccessTokenModel
)

/**
 * API Representation of a User and the authorization credentials
 * @property user The user
 * @property embedded The embedded details
 */
data class AuthorizedUserModel(
        @JsonUnwrapped val user: UserModel,
        @JsonProperty("_embedded") val embedded: AuthorizedUserEmbedded
)
