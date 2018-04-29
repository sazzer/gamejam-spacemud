package uk.co.grahamcox.space.galaxy

import uk.co.grahamcox.space.model.Id

/**
 * The ID of a Galaxy
 */
data class GalaxyId(override val id: String) : Id<String>
