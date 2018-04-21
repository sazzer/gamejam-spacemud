package uk.co.grahamcox.space.galaxy.generation

/**
 * Representation of the galaxy that we are generating
 * @property sectors The actual sectors that make up the galaxy
 */
class Galaxy(
        private val sectors: Array<Array<Sector>>
) {
    /** The width of the galaxy */
    val width = sectors.size

    /** The height of the galaxy */
    val height = sectors[0].size

    /**
     * Get the details of a single sector in the galaxy
     * @param x The x-ordinate of the sector
     * @param y The y-ordinate of the sector
     * @return the sector
     */
    fun getSector(x: Int, y: Int) = sectors[x][y]
}
