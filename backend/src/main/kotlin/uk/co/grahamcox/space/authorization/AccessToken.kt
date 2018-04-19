package uk.co.grahamcox.space.authorization

import uk.co.grahamcox.space.users.UserId
import java.time.Instant

/**
 * Representation of an Access Token
 * @property id The ID of the Access Token
 * @property userId The ID of the User that the Access Token represents
 * @property created When the Access Token was created
 * @property expires When the Access Token expires
 */
data class AccessToken(
        val id: AccessTokenId,
        val userId: UserId,
        val created: Instant,
        val expires: Instant
)
