package uk.co.grahamcox.space.species

import uk.co.grahamcox.space.galaxy.GalaxyId

/**
 * Details representing an entire Species
 * @property galaxyId The galaxy that the Species belongs to
 * @property name The name of the species
 * @property traits The traits of the species
 */
data class SpeciesData(
        val galaxyId: GalaxyId,
        val name: String,
        val traits: Map<SpeciesTraits, Int>
) {
    /**
     * Get the actual value for the given trait
     * @param trait The trait
     * @return the value for the trait
     */
    fun getTrait(trait: SpeciesTraits) = traits.getOrDefault(trait, 0)
}
