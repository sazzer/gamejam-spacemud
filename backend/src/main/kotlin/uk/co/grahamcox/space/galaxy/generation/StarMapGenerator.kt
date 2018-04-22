package uk.co.grahamcox.space.galaxy.generation

/**
 * Interface describing how to generate a new star map of the galaxy
 */
interface StarMapGenerator {
    /**
     * Generate a new star map
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated star map
     */
    fun generateGalaxy(size: Int, stars: Int) : StarMap
}
