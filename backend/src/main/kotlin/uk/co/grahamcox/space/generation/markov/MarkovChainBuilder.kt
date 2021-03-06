package uk.co.grahamcox.space.generation.markov

import uk.co.grahamcox.space.generation.markov.dao.MarkovChainDao
import uk.co.grahamcox.space.generation.markov.dao.MarkovChainFilters
import uk.co.grahamcox.space.model.Resource

/**
 * Mechanism by which a Markov Chain Generator can be obtained
 * @property markovChainDao The means to load Markov Chain configuration from the data store
 */
class MarkovChainBuilder(private val markovChainDao: MarkovChainDao) {
    /**
     * Build a Markov Chain Generator for the single set of config eith the given ID
     * @param id The ID of the Markov Chain to build
     * @return the Markov Chain Generator
     */
    fun buildSingleChain(id: MarkovChainId) : MarkovChainGenerator {
        val config = markovChainDao.getById(id)
        return build(config)
    }

    /**
     * Build a compelx Markov Chain Generator, that uses every configuration of a given type
     * @param type The type of Markov Chain to load
     * @return the Markov Chain Generator
     */
    fun buildComplexChain(type: String) : MarkovChainGenerator {
        val markovChains = markovChainDao.list(MarkovChainFilters(type = type))

        return MultipleMarkovChainGenerator(
                markovChains.data.map(this::build)
        )
    }

    /**
     * Actually build the generator for the given config
     * @param config The config to build the generator from
     * @return the generator
     */
    private fun build(config : Resource<MarkovChainId, MarkovChainData>) : MarkovChainGenerator {
        return SimpleMarkovChainGeneratorImpl(
                inputWords = config.data.corpus,
                prefixLength = config.data.prefix
        )
    }
}
