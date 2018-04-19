package uk.co.grahamcox.space.users.rest

import org.springframework.http.HttpStatus
import uk.co.grahamcox.space.rest.Problem
import java.net.URI

/**
 * Problem indicating that the requested user details already existed
 * @param instance A URI reference that identifies the specific occurrence of the problem
 * @param detail A human-readable explanation specific to this occurrence of the problem
 */
class DuplicateUserProblem(instance: URI?, detail: String?) : Problem(
        type = URI("tag:grahamcox.co.uk,2018,spacemud/users/problems/duplicate"),
        title = "Duplicate User Details",
        statusCode = HttpStatus.CONFLICT,
        instance = instance,
        detail = detail
)
