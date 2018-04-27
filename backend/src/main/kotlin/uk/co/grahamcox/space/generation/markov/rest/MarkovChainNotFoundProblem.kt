package uk.co.grahamcox.space.generation.markov.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.space.rest.Problem
import java.net.URI

/**
 * Problem indicating that the requested user could not be found
 * @param instance A URI reference that identifies the specific occurrence of the problem
 * @param detail A human-readable explanation specific to this occurrence of the problem
 */
class MarkovChainNotFoundProblem(instance: URI?, detail: String?) : Problem(
        type = URI("tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/not-found"),
        title = "Markov Chain not found",
        statusCode = HttpStatus.NOT_FOUND,
        instance = instance,
        detail = detail
)
