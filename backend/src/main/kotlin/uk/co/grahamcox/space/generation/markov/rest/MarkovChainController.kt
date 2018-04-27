package uk.co.grahamcox.space.generation.markov.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.generation.markov.dao.DuplicateNameException
import uk.co.grahamcox.space.generation.markov.dao.MarkovChainDao
import java.net.URI

/**
 * Controller for working with Markov Chain empire
 */
@RestController
@RequestMapping(value = ["/api/empire/markovChains"], produces = ["application/hal+json"])
class MarkovChainController(
        private val markovChainDao: MarkovChainDao,
        private val markovChainTranslator: MarkovChainTranslator,
        private val markovChainPageTranslator: MarkovChainPageTranslator
) {
    /**
     * Exception handler for when the markov chain to be resolved was unknown
     */
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleUnknownEmail(e: ResourceNotFoundException) = ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.parseMediaType("application/problem+json"))
            .body(MarkovChainNotFoundProblem(
                    instance = URI("tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/not-found/unknown-id"),
                    detail = "Unknown ID: ${e.id.id}"
            ))

    /**
     * Exception handler for when the markov chain to be resolved was unknown
     */
    @ExceptionHandler(DuplicateNameException::class)
    fun handleDuplicateName() = ResponseEntity.status(HttpStatus.CONFLICT)
            .contentType(MediaType.parseMediaType("application/problem+json"))
            .body(DuplicateMarkovChainProblem(
                    instance = URI("tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/duplicate/duplicate-name"),
                    detail = "Duplicate name"
            ))

    /**
     * Generate a list of all the Markov Chains
     */
    @RequestMapping(method = [RequestMethod.GET])
    fun list(): ResponseEntity<MarkovChainPageModel>? {
        val chains = markovChainDao.list()

        return ResponseEntity.status(HttpStatus.OK)
                .body(markovChainPageTranslator.translate(chains))
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
