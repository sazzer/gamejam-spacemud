package uk.co.grahamcox.space.generation.galaxy

import uk.co.grahamcox.space.generation.species.Species

/**
 * Some details of a single sector in the galaxy
 * @property totalCount The total number of stars in the sector
 * @property speciesCount The map of species to the number of stars they havein the sector
 */
data class Sector(
        val totalCount: Int,
        val speciesCount: Map<Species, Int>
) {
    /** The number of unpopulated stars in the sector */
    val unpopulatedCount = totalCount - speciesCount.values.sum()
}
