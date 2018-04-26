package uk.co.grahamcox.space.galaxy.species

/**
 * Details representing an entire Species
 * @property name The name of the species
 * @property traits The traits of the species
 */
data class Species(
        val name: String,
        private val traits: Map<SpeciesTraits, Int>
) {
    /**
     * Get the actual value for the given trait
     * @param trait The trait
     * @return the value for the trait
     */
    fun getTrait(trait: SpeciesTraits) = traits.getOrDefault(trait, 0)
}
