package uk.co.grahamcox.space.galaxy

import uk.co.grahamcox.space.galaxy.empire.Empire
import uk.co.grahamcox.space.galaxy.species.Species
import uk.co.grahamcox.space.galaxy.starmap.StarMap

/**
 * Representation of the entire galaxy
 */
data class Galaxy(
        private val starMap: StarMap,
        private val speciesEmpires: Map<Species, Empire>
) {
    /** The width of the galaxy */
    val width = starMap.width

    /** The height of the galaxy */
    val height = starMap.height

    /** The list of species in the galaxy */
    val species = speciesEmpires.keys.sortedBy { it.name }

    /**
     * Get the details of a single sector
     * @param coords The coords of the sector
     * @return the sector details
     */
    fun getSector(coords: Coords): Sector {
        val total = starMap.getSector(coords)
        val speciesCounts = speciesEmpires.mapValues { it.value.getSector(coords) }

        return Sector(total, speciesCounts)
    }
}
