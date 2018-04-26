package uk.co.grahamcox.space.generation.markov

import uk.co.grahamcox.space.model.Id

/**
 * The ID of a Markov Chain
 * @property id The actual ID
 */
data class MarkovChainId(override val id: String) : Id<String>
