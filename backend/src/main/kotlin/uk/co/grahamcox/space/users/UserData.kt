package uk.co.grahamcox.space.users

/**
 * Data representing a user record
 * @property email The users email address
 * @property directionality The users display name
 * @property password The users password
 */
data class UserData(
        val email: String,
        val displayName: String,
        val password: String
)
