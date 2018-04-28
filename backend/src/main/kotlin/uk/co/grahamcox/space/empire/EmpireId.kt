package uk.co.grahamcox.space.empire

import uk.co.grahamcox.space.model.Id

/**
 * The ID of a sector in an Empire
 */
data class EmpireId(override val id: String) : Id<String>
