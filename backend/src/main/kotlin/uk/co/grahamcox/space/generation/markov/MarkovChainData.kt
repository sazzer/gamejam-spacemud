package uk.co.grahamcox.space.generation.markov

/**
 * Data that makes up a Markov Chain
 * @property name The name of this Markov Chain
 * @property type The type of this Markov Chain
 * @property prefix The prefix value for this Markov Chain
 * @property corpus The corpus of words for this Markov Chain
 */
data class MarkovChainData(
        val name: String,
        val type: String,
        val prefix: Int,
        val corpus: List<String>
)
