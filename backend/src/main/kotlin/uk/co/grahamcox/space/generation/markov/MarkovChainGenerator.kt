package uk.co.grahamcox.space.generation.markov

import org.apache.commons.math3.random.RandomGenerator

interface MarkovChainGenerator {
    /**
     * Generate a new name from the configured chains
     * @param rng The RNG to use
     * @return the generated name
     */
    fun generate(rng: RandomGenerator): String

    /**
     * Generate a number of names from the configured chains
     * @param rng The RNG to use
     * @param number The number of names to generate
     * @return the generated names
     */
    fun generate(rng: RandomGenerator, number: Int): List<String> = (0..number).map { generate(rng) }
}
