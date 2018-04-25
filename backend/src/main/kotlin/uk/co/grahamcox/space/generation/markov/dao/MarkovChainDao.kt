package uk.co.grahamcox.space.generation.markov.dao

import uk.co.grahamcox.space.generation.markov.MarkovChainData
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource

/**
 * DAO interface for accessing Markov Chain data
 */
interface MarkovChainDao {
    /**
     * Get a list of all the Markov Chains in the system
     * @return the list of Markov Chains
     */
    fun list() : Page<MarkovChainId, MarkovChainData>

    /**
     * Get a single Markov Chain by ID
     * @param id The Id of the chain
     * @return the chain
     */
    fun getById(id: MarkovChainId) : Resource<MarkovChainId, MarkovChainData>

    /**
     * Create a new Markov Chain
     * @param markovChain The data representing the new chain
     * @return the new chain
     */
    fun create(markovChain: MarkovChainData) : Resource<MarkovChainId, MarkovChainData>

    /**
     * Update an existing Markov Chain
     * @param id The ID of the Markov Chain to update
     * @param markovChain The new data for the Markov Chain
     * @return the newly updated Markov Chain
     */
    fun update(id: MarkovChainId, markovChain: MarkovChainData) : Resource<MarkovChainId, MarkovChainData>

    /**
     * Delete a Markov Chain
     * @param id The ID of the Markov Chain to delete
     */
    fun delete(id: MarkovChainId)
}
