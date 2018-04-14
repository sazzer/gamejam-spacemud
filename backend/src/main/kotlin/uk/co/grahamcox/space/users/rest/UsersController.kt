package uk.co.grahamcox.space.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.space.users.dao.UserDao
import java.net.URI

/**
 * Controller for working with User records
 */
@RestController
@RequestMapping("/api/users")
class UsersController(
        private val userDao: UserDao,
        private val userTranslator: UserTranslator
) {
    /**
     * Get the user that has the specified ID
     * @param id The ID of the user
     */
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET], produces = ["application/hal+json"])
    fun getUserById(@PathVariable("id") id: String) {
        TODO()
    }

    /**
     * Get the user that has the specified Email Address
     * @param email The email address to look up
     * @return the user with the email address
     */
    @RequestMapping(value = ["/emails/{email}"], method = [RequestMethod.GET], produces = ["application/hal+json"])
    fun getUserByEmail(@PathVariable("email") email: String): ResponseEntity<*> {
        val user = userDao.getByEmail(email)

        return if (user != null) {
            ResponseEntity.status(HttpStatus.OK)
                    .body(userTranslator.translate(user))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.parseMediaType("application/problem+json"))
                    .body(UserNotFoundProblem(
                            instance = URI("tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found/unknown-email"),
                            detail = "Unknown email address: $email"
                    ))
        }
    }
}
