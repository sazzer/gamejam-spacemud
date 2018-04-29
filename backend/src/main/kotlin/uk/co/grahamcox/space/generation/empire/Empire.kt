package uk.co.grahamcox.space.generation.empire

import uk.co.grahamcox.space.galaxy.Coords

/**
 * Representation of the a single Empire in the galaxy
 * @property sectors The actual sectors that make up the Empire
 */
open class Empire(
        private val sectors: Array<IntArray>
) {
    /** The width of the galaxy */
    val width = sectors.size

    /** The height of the galaxy */
    val height = sectors[0].size

    /**
     * Get the details of a single sector in the Empire
     * @param coords The coords of the sector
     * @return the number of stars in this sector that belong to the Empire
     */
    fun getSector(coords: Coords) = sectors[coords.x][coords.y]
}

/**
 * Mutable subclass of the Empire
 */
class MutableEmpire(
        private val sectors: Array<IntArray>
) : Empire(sectors) {
    /**
     * Update the value for the given sector
     * @param coords The coords of the sector
     * @param newValue The new value for the sector
     */
    fun setSector(coords: Coords, newValue: Int) {
        sectors[coords.x][coords.y] = newValue
    }
}
