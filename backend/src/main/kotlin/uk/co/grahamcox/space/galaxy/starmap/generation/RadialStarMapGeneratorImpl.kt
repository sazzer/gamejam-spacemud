package uk.co.grahamcox.space.galaxy.starmap.generation

import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory

/**
 * StarMap Generator that works by generating a random angle around the centre and a random distance from the centre for
 * each of the stars in the galaxy
 */
abstract class RadialStarMapGeneratorImpl : StarMapGeneratorImpl() {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(RadialStarMapGeneratorImpl::class.java)
    }

    /**
     * Generate the star map - literally an array or array of ints, where each entry is the number of stars in that
     * sector of space
     * @param rng The RNG to use
     * @param size The number of sectors wide and high
     * @param stars The number of stars in the galaxy
     * @return the generated star map
     */
    override fun generateStarMap(rng: RandomGenerator, size: Int, stars: Int): Array<IntArray> {
        val starMap = Array(size) {
            IntArray(size) {
                0
            }
        }
        val halfSize = size / 2.0
        LOG.debug("Half Size: {}", halfSize)
        for (i in 0..stars) {
            var x: Int
            var y: Int
            var degrees: Double
            var angle: Double
            var distance: Double
            do {
                degrees = rng.nextDouble() * 360
                angle = Math.toRadians(degrees)
                distance = generateDistance(rng, halfSize)

                x = Math.floor((Math.cos(angle) * distance) + halfSize).toInt()
                y = Math.floor((Math.sin(angle) * distance) + halfSize).toInt()
            } while (x >= size || x < 0 || y >= size || y < 0)

            LOG.debug("Star {} - Angle: ({} = {}), Distance: {}, Coords: ({}, {})",
                    i, degrees, angle, distance, x, y)

            starMap[x][y]++
        }
        return starMap
    }

    /**
     * Generate the distance from the galactic core
     * @param rng The RNG to use
     * @param halfSize The half-size of the galaxy - the distance from the core to the edge
     * @return the generated distance
     */
    abstract fun generateDistance(rng: RandomGenerator, halfSize: Double): Double
}
