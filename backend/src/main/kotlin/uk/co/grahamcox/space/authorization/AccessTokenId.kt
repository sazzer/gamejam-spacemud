package uk.co.grahamcox.space.authorization

import uk.co.grahamcox.space.model.Id

/**
 * Representation of the ID of an Access Token
 */
data class AccessTokenId(override val id: String) : Id<String>
