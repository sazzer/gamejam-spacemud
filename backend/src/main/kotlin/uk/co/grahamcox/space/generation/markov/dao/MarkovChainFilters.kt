package uk.co.grahamcox.space.generation.markov.dao

/**
 * Filters that can be applied when listing the Markov Chains to return
 * @property type Match only Markov Chains of the given type
 */
data class MarkovChainFilters(
        val type: String? = null
)
