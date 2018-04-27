package uk.co.grahamcox.space.generation.starmap

import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.galaxy.starmap.StarMap

/**
 * Core implementation of the StarMap Generator
 */
abstract class StarMapGeneratorImpl : StarMapGenerator {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(StarMapGeneratorImpl::class.java)
    }

    /**
     * Generate a new galaxy
     * @param rng The RNG to use
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated galaxy
     */
    override fun generateGalaxy(rng: RandomGenerator, size: Int, stars: Int): StarMap {
        LOG.debug("Generating galaxy of size {}x{}, with {} stars", size, size, stars)

        val starMap = generateStarMap(rng, size, stars)

        return StarMap(starMap)
    }

    /**
     * Generate the star map - literally an array or array of ints, where each entry is the number of stars in that
     * sector of space
     * @param rng The RNG to use
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated star map
     */
    protected abstract fun generateStarMap(rng: RandomGenerator, size: Int, stars: Int): Array<IntArray>
}
