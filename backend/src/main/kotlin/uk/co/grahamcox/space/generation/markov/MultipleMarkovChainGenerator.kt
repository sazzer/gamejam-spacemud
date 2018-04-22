package uk.co.grahamcox.space.generation.markov

import org.apache.commons.math3.random.RandomGenerator

/**
 * Wrapper around a list of Markov Chain Generators that will generate a random word from a random generator
 * @property generators The generators to use
 */
class MultipleMarkovChainGenerator(
        private val generators: List<MarkovChainGenerator>
) : MarkovChainGenerator {
    /**
     * Generate a new name from the configured chains
     * @param rng The RNG to use
     * @return the generated name
     */
    override fun generate(rng: RandomGenerator): String {
        val generatorIndex = rng.nextInt(generators.size)
        val generator = generators[generatorIndex]

        return generator.generate(rng)
    }
}
