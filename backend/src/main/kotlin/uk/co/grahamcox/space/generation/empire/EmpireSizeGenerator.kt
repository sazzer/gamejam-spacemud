package uk.co.grahamcox.space.generation.empire

import org.apache.commons.math3.distribution.NormalDistribution
import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.generation.species.Species
import uk.co.grahamcox.space.species.SpeciesTraits

/**
 * Means to randomly generate the size of an empire
 */
class EmpireSizeGenerator {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(EmpireSizeGenerator::class.java)
    }
    /**
     * Generate the number of stars that the given species will have in their empire
     * @param rng The RNG to use
     * @param species The species to use
     * @param mean The mean number of stars
     * @return the number of stars
     */
    fun generate(rng: RandomGenerator, species: Species, mean: Int) : Int {
        val shift = mean / 10
        val actualShift = species.getTrait(SpeciesTraits.EXPANSIVE) * shift
        val actualMean = mean + actualShift
        val sd = shift * 2

        LOG.debug("Generating empire size for species {}, with mean {} and sd {}", species, actualMean, sd)

        val distribution = NormalDistribution(rng, actualMean.toDouble(), sd.toDouble())
        val stars = distribution.sample().toInt()
        LOG.debug("Empire size for species {}: {}", species, stars)

        return stars
    }
}
