package uk.co.grahamcox.space.galaxy.generation

import org.apache.commons.math3.distribution.ChiSquaredDistribution

/**
 * Implementation of the [RadialGalaxyGeneratorImpl] that uses a Chi-Squared Distribution for distances
 */
class ChiSquaredRadialGalaxyGeneratorImpl : RadialGalaxyGeneratorImpl() {
    /** The Distance Distribution to use with the RNG */
    private val distanceDistribution = ChiSquaredDistribution(rng, 2.5)

    /**
     * Generate the distance from the galactic core
     * @param halfSize The half-size of the galaxy - the distance from the core to the edge
     * @return the generated distance
     */
    override fun generateDistance(halfSize: Double) =
            distanceDistribution.sample() * (halfSize / distanceDistribution.numericalMean)

}
