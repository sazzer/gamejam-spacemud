package uk.co.grahamcox.space.users.rest

/**
 * Exception to indicate that the requested email address was unknown
 * @property email The email address that was unknown
 */
class UnknownEmailException(val email: String) : RuntimeException()
