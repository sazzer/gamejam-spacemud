package uk.co.grahamcox.space.authorization.rest

/**
 * Exception to indicate that deserializing an Access Token failed
 * @property accessToken The access token that failed to deserialize
 * @param message The error message
 * @param rootCause The root cause
 */
class InvalidAccessTokenException(val accessToken: String, message: String?, rootCause: Throwable?)
    : RuntimeException(message, rootCause)
