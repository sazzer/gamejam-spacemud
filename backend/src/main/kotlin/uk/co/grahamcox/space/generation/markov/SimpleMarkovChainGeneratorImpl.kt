package uk.co.grahamcox.space.generation.markov

import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory

/**
 * Markov Chain Generator for generating random names that seem believable
 */
class SimpleMarkovChainGeneratorImpl(
        inputWords: List<String>,
        prefixLength: Int
) : MarkovChainGenerator {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(SimpleMarkovChainGeneratorImpl::class.java)
    }

    /** The set of word prefixes */
    private val prefixes: List<String>

    private val chains: Map<String, List<String>>

    init {
        val wordsOfLength = inputWords.filter { it.length >= prefixLength }
                .map { it.toUpperCase() }

        prefixes = wordsOfLength
                .map { it.substring(0, prefixLength) }
                .toSet()
                .toList()
        LOG.debug("Prefixes: {}", prefixes)

        val tempChains = mutableMapOf<String, MutableSet<String>>()
        wordsOfLength
                .forEach {
                    LOG.trace("Considering input: {}", it)
                    for (i in 0 until it.length - prefixLength) {
                        val prefix = it.substring(i, i + prefixLength)
                        val next = it.substring(i + prefixLength, i + prefixLength + 1)
                        LOG.trace("Building chain. Prefix={}, next={}", prefix, next)
                        tempChains.getOrPut(prefix) { mutableSetOf() }.add(next)
                    }
                }
        LOG.debug("Chains: {}", tempChains)

        chains = tempChains.mapValues { it.value.toList() }
    }

    /**
     * Generate a new name from the configured chains
     * @param rng The RNG to use
     * @return the generated name
     */
    override fun generate(rng: RandomGenerator): String {
        val length = rng.nextInt(15) + 5
        LOG.debug("Building name of length: {}", length)
        val result = StringBuilder()

        result.append(prefixes[rng.nextInt(prefixes.size)])
        LOG.trace("Prefix: {}", result)

        while (result.length < length) {
            val nextPrefix = result.substring(result.length - 2, result.length)
            LOG.trace("Next Prefix: {}", nextPrefix)

            val nextValues = chains[nextPrefix] ?: break

            result.append(nextValues[rng.nextInt(nextValues.size)])
            LOG.trace("So far: {}", result)
        }

        LOG.debug("Built name: {}", result)
        return result.toString()
    }
}
