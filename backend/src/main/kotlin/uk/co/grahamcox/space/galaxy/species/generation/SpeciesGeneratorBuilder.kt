package uk.co.grahamcox.space.galaxy.species.generation

import uk.co.grahamcox.space.generation.markov.MarkovChainBuilder

/**
 * Builder to build the Species Generator from the data store
 * @property markovChainBuilder The Markov Chain Builder to use
 * @property markovChainType The type of Markov Chain to load
 */
class SpeciesGeneratorBuilder(
        private val markovChainBuilder: MarkovChainBuilder,
        private val markovChainType: String = "species"
) {
    /**
     * Build the Species Generator, providing it with a Markov Chain Generator
     * @return the Species Generator
     */
    fun build() = SpeciesGenerator(markovChainBuilder.buildComplexChain(markovChainType))
}
