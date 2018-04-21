package uk.co.grahamcox.space.galaxy.generation

/**
 * Interface describing how to generate a new galaxy
 */
interface GalaxyGenerator {
    /**
     * Generate a new galaxy
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated galaxy
     */
    fun generateGalaxy(size: Int, stars: Int) : Galaxy
}
