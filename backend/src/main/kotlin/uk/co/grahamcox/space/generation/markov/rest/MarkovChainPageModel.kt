package uk.co.grahamcox.space.generation.markov.rest

import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.grahamcox.space.rest.hal.Link

/**
 * Links relevant to a Markov Chain page
 * @property self The self link
 */
data class MarkovChainPageLinks(
        val self: Link
)

/**
 * Embedded resources in a Markov Chain page
 * @property chains The embedded markov chains
 */
data class MarkovChainPageEmbedded(
        val chains: List<MarkovChainModel>
)

/**
 * Representation of a page of Markov Chains
 * @property links The links for this page
 * @property embedded The embedded resources
 * @property totalCount The total number of matching Markov Chains
 */
data class MarkovChainPageModel(
        @JsonProperty("_links") val links: MarkovChainPageLinks,
        @JsonProperty("_embedded") val embedded: MarkovChainPageEmbedded,
        val totalCount: Int
)
