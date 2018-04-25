package uk.co.grahamcox.space.generation.markov.rest

import uk.co.grahamcox.space.generation.markov.MarkovChainData
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.rest.hal.Link
import uk.co.grahamcox.space.rest.hal.Meta
import uk.co.grahamcox.space.rest.hal.buildUri

/**
 * Translator between the API and Model representation of a Markov Chain
 */
class MarkovChainTranslator {
    /**
     * Translate a Model representation into an API one
     * @param input The input to translate
     * @return the translated value
     */
    fun translate(input: Resource<MarkovChainId, MarkovChainData>) : MarkovChainModel {
        return MarkovChainModel(
                links = MarkovChainLinks(
                        self = Link(
                                href = MarkovChainController::getById.buildUri(input.identity.id.id)
                        )
                ),
                meta = Meta(
                        created = input.identity.created,
                        updated = input.identity.updated,
                        version = input.identity.version
                ),
                data = MarkovChainInputModel(
                        name = input.data.name,
                        type = input.data.type,
                        prefix = input.data.prefix,
                        corpus = input.data.corpus
                )
        )
    }

    /**
     * Translate an API Input representation into the Model representation
     * @param input The input to translate
     * @return the translated value
     */
    fun translate(input: MarkovChainInputModel) : MarkovChainData {
        return MarkovChainData(
                name = input.name,
                type = input.type,
                prefix = input.prefix,
                corpus = input.corpus
        )
    }
}
