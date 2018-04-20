package uk.co.grahamcox.space.galaxy.generation

/**
 * Representation of the galaxy that we are generating
 * @property width The number of sectors wide
 * @property height The number of sectors high
 */
data class Galaxy(
        val width: Int,
        val height: Int
) {
    /**
     * Get the details of a single sector in the galaxy
     * @param x The x-ordinate of the sector
     * @param y The y-ordinate of the sector
     * @return the sector
     */
    fun getSector(x: Int, y: Int): Sector {
        TODO()
    }
}
