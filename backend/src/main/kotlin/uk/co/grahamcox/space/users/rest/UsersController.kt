package uk.co.grahamcox.space.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.space.users.UserData
import uk.co.grahamcox.space.users.dao.DuplicateUserException
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
     * Exception handler for when the email address to be resolved was unknown
     */
    @ExceptionHandler(UnknownEmailException::class)
    fun handleUnknownEmail(e: UnknownEmailException) = ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.parseMediaType("application/problem+json"))
            .body(UserNotFoundProblem(
                    instance = URI("tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found/unknown-email"),
                    detail = "Unknown email address: ${e.email}"
            ))

    /**
     * Exception handler for when the user details being created were a duplicate
     */
    @ExceptionHandler(DuplicateUserException::class)
    fun handleDuplicateUser(e: DuplicateUserException) = ResponseEntity.status(HttpStatus.CONFLICT)
            .contentType(MediaType.parseMediaType("application/problem+json"))
            .body(DuplicateUserProblem(
                    instance = URI("tag:grahamcox.co.uk,2018,spacemud/users/problems/duplicate/duplicate-email"),
                    detail = "Duplicate email address"
            ))

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
    fun getUserByEmail(@PathVariable("email") email: String): ResponseEntity<UserModel> {
        val user = userDao.getByEmail(email) ?: throw UnknownEmailException(email)

        return ResponseEntity.status(HttpStatus.OK)
                .body(userTranslator.translate(user))
    }

    /**
     * Create a new user in the system
     * @param data The data representing the user to create
     * @return the response from creating the user
     */
    @RequestMapping(method = [RequestMethod.POST], produces = ["application/hal+json"])
    fun createUser(@RequestBody data: CreateUserModel): ResponseEntity<UserModel> {
        val userData = UserData(
                email = data.email,
                displayName = data.displayName,
                password = data.password
        )

        val createdUser = userDao.create(userData)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userTranslator.translate(createdUser))
    }
}
