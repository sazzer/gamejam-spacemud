package uk.co.grahamcox.space.galaxy.generation

/**
 * Interface describing how to generate a new galaxy
 */
interface GalaxyGenerator {
    /**
     * Generate a new galaxy
     * @param width The number of sectors wide
     * @param height The number of sectors high
     * @param stars The number of stars in the galaxy
     * @return the generated galaxy
     */
    fun generateGalaxy(width: Int, height: Int, stars: Int) : Galaxy
}
