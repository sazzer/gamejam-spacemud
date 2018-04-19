package uk.co.grahamcox.space.users.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uk.co.grahamcox.space.users.PasswordHasher
import uk.co.grahamcox.space.users.UserData
import uk.co.grahamcox.space.users.dao.DuplicateUserException
import uk.co.grahamcox.space.users.dao.UserDao
import java.net.URI

/**
 * Controller for creating User records
 */
@RestController
@RequestMapping("/api/users")
class CreateUserController(
        private val userDao: UserDao,
        private val passwordHasher: PasswordHasher,
        private val userTranslator: UserTranslator
) {
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
     * Create a new user in the system
     * @param data The data representing the user to create
     * @return the response from creating the user
     */
    @RequestMapping(method = [RequestMethod.POST], produces = ["application/hal+json"])
    fun createUser(@RequestBody data: CreateUserModel): ResponseEntity<UserModel> {
        val userData = UserData(
                email = data.email,
                displayName = data.displayName,
                password = passwordHasher.hashPassword(data.password)
        )

        val createdUser = userDao.create(userData)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userTranslator.translate(createdUser))
    }
}
