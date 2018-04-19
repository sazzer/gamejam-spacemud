package uk.co.grahamcox.space.authorization.rest

import uk.co.grahamcox.space.authorization.AccessToken
import java.time.Clock
import java.time.Duration

/**
 * Means to translate an Access Token into the JSON form
 */
class AccessTokenTranslator(
        private val clock: Clock,
        private val accessTokenSerializer: AccessTokenSerializer
) {
    /**
     * Translate the given Access Token into the JSON version
     * @param accessToken The access token to translate
     * @return the JSON version
     */
    fun translate(accessToken: AccessToken) : AccessTokenModel {
        val now = clock.instant()
        val expiryDuration = Duration.between(now, accessToken.expires)

        val serialized = accessTokenSerializer.serialize(accessToken
        )
        return AccessTokenModel(
                accessToken = serialized,
                expires = expiryDuration.seconds
        )
    }
}
