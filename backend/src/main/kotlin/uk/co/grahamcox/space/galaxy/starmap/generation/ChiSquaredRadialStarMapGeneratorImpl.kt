package uk.co.grahamcox.space.galaxy.starmap.generation

import org.apache.commons.math3.distribution.ChiSquaredDistribution
import org.apache.commons.math3.random.RandomGenerator

/**
 * Implementation of the [RadialStarMapGeneratorImpl] that uses a Chi-Squared Distribution for distances
 */
class ChiSquaredRadialStarMapGeneratorImpl : RadialStarMapGeneratorImpl() {

    /**
     * Generate the distance from the galactic core
     * @param rng The RNG to use
     * @param halfSize The half-size of the galaxy - the distance from the core to the edge
     * @return the generated distance
     */
    override fun generateDistance(rng: RandomGenerator, halfSize: Double): Double {
        val distanceDistribution = ChiSquaredDistribution(rng, 2.5)
        return distanceDistribution.sample() * (halfSize / distanceDistribution.numericalMean)
    }
}
