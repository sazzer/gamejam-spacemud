package uk.co.grahamcox.space.users.rest

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Incoming model for creating a new user
 * @property email The email address of the user
 * @property displayName The display name of the user
 * @property password The password for the user
 */
data class CreateUserModel @JsonCreator constructor(
        val email: String,
        val displayName: String,
        val password: String
)
