package uk.co.grahamcox.space.galaxy.generation

import org.slf4j.LoggerFactory

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
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated galaxy
     */
    override fun generateGalaxy(size: Int, stars: Int): StarMap {
        LOG.debug("Generating galaxy of size {}x{}, with {} stars", size, size, stars)

        val starMap = generateStarMap(size, stars)

        val sectorMap = starMap.map { row ->
            row.map { Sector(it) }.toTypedArray()
        }.toTypedArray()

        return StarMap(sectorMap)
    }

    /**
     * Generate the star map - literally an array or array of ints, where each entry is the number of stars in that
     * sector of space
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated star map
     */
    protected abstract fun generateStarMap(size: Int, stars: Int): Array<IntArray>
}
