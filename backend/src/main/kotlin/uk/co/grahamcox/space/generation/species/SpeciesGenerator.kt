package uk.co.grahamcox.space.generation.species

import org.apache.commons.lang3.StringUtils
import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.species.SpeciesTraits
import uk.co.grahamcox.space.generation.markov.MarkovChainGenerator

/**
 * Mechanism to generate a new species
 * @property nameGenerator The name generator to use
 * @property traitShift The number of traits to shift
 * @property maxShift The maximum amount to shift any trait by
 */
class SpeciesGenerator(
        private val nameGenerator: MarkovChainGenerator,
        private val traitShift: Int = SpeciesTraits.values().size,
        private val maxShift: Int = traitShift / 3
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(SpeciesGenerator::class.java)
    }

    /**
     * Generate a new species
     * @param rng The rng to use
     * @return the generated species
     */
    fun generate(rng: RandomGenerator) : Species {
        val name = StringUtils.capitalize(nameGenerator.generate(rng).toLowerCase())
        LOG.debug("Generated species name: {}", name)

        // Set up the map with initial values
        val traits = mutableMapOf(
                *SpeciesTraits.values().map { it to 0 }.toTypedArray()
        )

        val numberOfTraits = SpeciesTraits.values().size

        listOf(-1, 1).forEach { amountToShift ->
            for (i in 0 until traitShift / 2) {
                var trait: SpeciesTraits

                do {
                    val traitToShift = rng.nextInt(numberOfTraits)
                    trait = SpeciesTraits.values()[traitToShift]
                } while (Math.abs(traits.getOrDefault(trait, 0)) > maxShift)

                LOG.trace("Shifting trait {} by {}", trait, amountToShift)

                val newAmount = traits.getOrDefault(trait, 0) + amountToShift
                traits[trait] = newAmount
            }
        }
        LOG.debug("Species traits: {}", traits)

        return Species(name, traits)
    }
}
