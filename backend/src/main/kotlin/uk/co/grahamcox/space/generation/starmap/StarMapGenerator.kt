package uk.co.grahamcox.space.generation.starmap

import org.apache.commons.math3.random.RandomGenerator
import uk.co.grahamcox.space.galaxy.starmap.StarMap

/**
 * Interface describing how to generate a new star map of the galaxy
 */
interface StarMapGenerator {
    /**
     * Generate a new star map
     * @param rng The RNG to use
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated star map
     */
    fun generateGalaxy(rng: RandomGenerator, size: Int, stars: Int) : StarMap
}
