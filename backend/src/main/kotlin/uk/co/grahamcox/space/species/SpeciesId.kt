package uk.co.grahamcox.space.species

import uk.co.grahamcox.space.model.Id

/**
 * The ID of a Species
 */
data class SpeciesId(override val id: String) : Id<String>
