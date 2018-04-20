package uk.co.grahamcox.space.authorization.rest

import uk.co.grahamcox.space.authorization.AccessToken

/**
 * Means to convert the Access Token to a serialized form and back
 */
interface AccessTokenSerializer {
    /**
     * Serialize the Access Token into a string
     * @param the access token
     * @return the string
     */
    fun serialize(accessToken: AccessToken) : String

    /**
     * Deserialize the Access Token from a string
     * @param accessToken The string
     * @return the access token
     */
    fun deserialize(accessToken: String) : AccessToken
}
