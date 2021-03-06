package uk.co.grahamcox.space.users.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.space.rest.Problem
import java.net.URI

/**
 * Problem indicating that the authentication has failed
 * @param instance A URI reference that identifies the specific occurrence of the problem
 * @param detail A human-readable explanation specific to this occurrence of the problem
 */
class AuthenticationFailureProblem(instance: URI?, detail: String?) : Problem(
        type = URI("tag:grahamcox.co.uk,2018,spacemud/users/problems/authentication"),
        title = "Authentication Failure",
        statusCode = HttpStatus.BAD_REQUEST,
        instance = instance,
        detail = detail
)
