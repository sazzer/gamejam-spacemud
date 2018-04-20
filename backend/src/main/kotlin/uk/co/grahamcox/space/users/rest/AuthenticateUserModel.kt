package uk.co.grahamcox.space.users.rest

/**
 * Request Payload for authenticating a user
 * @property email The email of the user to authenticate
 * @property password The password of the user to authenticate
 */
data class AuthenticateUserModel(
        val email: String,
        val password: String
)
