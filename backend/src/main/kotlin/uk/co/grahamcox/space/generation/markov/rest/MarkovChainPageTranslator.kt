package uk.co.grahamcox.space.generation.markov.rest

import uk.co.grahamcox.space.generation.markov.MarkovChainData
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.rest.hal.Link
import uk.co.grahamcox.space.rest.hal.buildUri

/**
 * Translator for pages of Markov Chains
 * @property singleTranslator The translator for a single Markov Chain
 */
class MarkovChainPageTranslator(
        private val singleTranslator: MarkovChainTranslator
) {
    /**
     * Translate the given Page into the API Response
     * @param input The input to translate
     * @return the translated value
     */
    fun translate(input: Page<MarkovChainId, MarkovChainData>) : MarkovChainPageModel {
        return MarkovChainPageModel(
                links = MarkovChainPageLinks(
                        self = Link(
                                href = MarkovChainController::list.buildUri()
                        )
                ),
                embedded = MarkovChainPageEmbedded(
                        chains = input.data.map(singleTranslator::translate)
                ),
                totalCount = input.totalCount
        )
    }
}
