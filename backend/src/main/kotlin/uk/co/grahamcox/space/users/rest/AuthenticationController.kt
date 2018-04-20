package uk.co.grahamcox.space.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.space.users.PasswordHasher
import uk.co.grahamcox.space.users.dao.UserDao
import java.net.URI

/**
 * Controller for authenticating a user
 */
@RestController
@RequestMapping(value = ["/api/authenticate"])
class AuthenticationController(
        private val userDao: UserDao,
        private val passwordHasher: PasswordHasher,
        private val userTranslator: AuthorizedUserTranslator
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
     * Exception handler for when the password was invalid
     */
    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPassword() = ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.parseMediaType("application/problem+json"))
            .body(AuthenticationFailureProblem(
                    instance = URI("tag:grahamcox.co.uk,2018,spacemud/users/problems/authentication/invalid-password"),
                    detail = "Invalid Password"
            ))

    /**
     * Handle authentication of the user
     *
     */
    @RequestMapping(method = [RequestMethod.POST], produces = ["application/hal+json"])
    fun authenticate(@RequestBody request: AuthenticateUserModel) : ResponseEntity<AuthorizedUserModel> {
        val user = userDao.getByEmail(request.email) ?: throw UnknownEmailException(request.email)

        if (!passwordHasher.comparePasswords(request.password, user.data.password)) {
            throw InvalidPasswordException()
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(userTranslator.translate(user))
    }
}
