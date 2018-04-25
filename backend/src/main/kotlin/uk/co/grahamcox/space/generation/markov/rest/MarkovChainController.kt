package uk.co.grahamcox.space.generation.markov.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.generation.markov.dao.MarkovChainDao

/**
 * Controller for working with Markov Chain generation
 */
@RestController
@RequestMapping(value = ["/api/generation/markovChains"], produces = ["application/hal+json"])
class MarkovChainController(
        private val markovChainDao: MarkovChainDao,
        private val markovChainTranslator: MarkovChainTranslator
) {
    /**
     * Generate a list of all the Markov Chains
     */
    @RequestMapping(method = [RequestMethod.GET])
    fun list() {
        TODO()
    }

    /**
     * Get the details of a single Markov Chain by ID
     * @param id The ID of the Markov Chain
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable("id") id: String): ResponseEntity<MarkovChainModel> {
        val markovChain = markovChainDao.getById(MarkovChainId(id))

        return ResponseEntity.status(HttpStatus.OK)
                .body(markovChainTranslator.translate(markovChain))
    }

    /**
     * Create a new Markov Chain
     */
    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody input: MarkovChainInputModel): ResponseEntity<MarkovChainModel> {
        val markovChain = markovChainTranslator.translate(input)
        val result = markovChainDao.create(markovChain)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(markovChainTranslator.translate(result))
    }

    /**
     * Update the details of a single Markov Chain by ID
     * @param id The ID of the Markov Chain
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun update(@PathVariable("id") id: String, @RequestBody input: MarkovChainInputModel): ResponseEntity<MarkovChainModel> {
        val markovChain = markovChainTranslator.translate(input)
        val result = markovChainDao.update(MarkovChainId(id), markovChain)
        return ResponseEntity.status(HttpStatus.OK)
                .body(markovChainTranslator.translate(result))
    }

    /**
     * Delete a single Markov Chain by ID
     * @param id The ID of the Markov Chain
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable("id") id: String): ResponseEntity<Void>? {
        markovChainDao.delete(MarkovChainId(id))

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build<Void>()
    }
}
