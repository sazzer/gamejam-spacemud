package uk.co.grahamcox.space.generation.markov.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped
import uk.co.grahamcox.space.rest.hal.Link
import uk.co.grahamcox.space.rest.hal.Meta

/**
 * Links relevant to a Markov Chain entry
 * @property self The self link
 */
data class MarkovChainLinks(
        val self: Link
)

/**
 * Input Model representation of a Markov Chain
 * @property name The name of this Markov Chain
 * @property type The type of this Markov Chain
 * @property prefix The prefix value for this Markov Chain
 * @property corpus The corpus of words for this Markov Chain
 */
data class MarkovChainInputModel(
        val name: String,
        val type: String,
        val prefix: Int,
        val corpus: List<String>
)

/**
 * Model representation of a Markov Chain
 * @property links Links relevant to this Markov Chain
 * @property meta The metadata for this Markov Chain
 * @property data The actual data of the Markov Chain
 */
data class MarkovChainModel(
        @JsonProperty("_links") val links: MarkovChainLinks,
        @JsonProperty("_meta") val meta: Meta,
        @JsonUnwrapped val data: MarkovChainInputModel
)
