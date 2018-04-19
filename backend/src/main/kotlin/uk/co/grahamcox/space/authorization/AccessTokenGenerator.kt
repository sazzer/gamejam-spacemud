package uk.co.grahamcox.space.authorization

import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.users.UserId
import java.time.Clock
import java.time.temporal.TemporalAmount

/**
 * Means to generate an access token for a user
 * @property clock The clock
 * @property duration The validity duration of the token
 * @property accessTokenIdGenerator The means to generate the Access Token ID
 */
class AccessTokenGenerator(
        private val clock: Clock,
        private val duration: TemporalAmount,
        private val accessTokenIdGenerator: AccessTokenIdGenerator) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(AccessTokenGenerator::class.java)
    }

    /**
     * Generate an access token for the user
     * @param user The user
     * @return the access token
     */
    fun generate(user: UserId) : AccessToken {
        val now = clock.instant()
        val expires = now.plus(duration)
        val id = accessTokenIdGenerator.generate()

        val accessToken = AccessToken(
                id = id,
                userId = user,
                created = now,
                expires = expires
        )
        LOG.debug("Generated access token {} for user {}", accessToken, user)
        return accessToken
    }
}
