package uk.co.grahamcox.space.users

import uk.co.grahamcox.space.model.Id

/**
 * Representation of a User ID
 */
data class UserId(override val id: String) : Id<String>
