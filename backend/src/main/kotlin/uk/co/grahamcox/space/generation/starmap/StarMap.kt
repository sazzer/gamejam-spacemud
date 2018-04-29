package uk.co.grahamcox.space.generation.starmap

import uk.co.grahamcox.space.galaxy.Coords

/**
 * Representation of the galaxy that we are generating
 * @property sectors The actual sectors that make up the galaxy
 */
class StarMap(
        private val sectors: Array<IntArray>
) {
    /** The width of the galaxy */
    val width = sectors.size

    /** The height of the galaxy */
    val height = sectors[0].size

    /**
     * Get the details of a single sector in the galaxy
     * @param coords The coords of the sector
     * @return the sector
     */
    fun getSector(coords: Coords) = sectors[coords.x][coords.y]
}
