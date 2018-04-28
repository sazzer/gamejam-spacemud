package uk.co.grahamcox.space.sector

import uk.co.grahamcox.space.model.Id

/**
 * The ID of a Sector in a Galaxy
 */
data class SectorId(override val id: String) : Id<String>
