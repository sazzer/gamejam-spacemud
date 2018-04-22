package uk.co.grahamcox.space.galaxy.species

/**
 * Details representing an entire Species
 * @property name The name of the species
 * @property traits The traits of the species
 */
data class Species(
        val name: String,
        val traits: Map<SpeciesTraits, Int>
)
