package uk.co.grahamcox.space.authorization

/**
 * Interface describing how to generate an Access Token ID
 */
interface AccessTokenIdGenerator {
    /**
     * Generate an ID for an Access Token
     */
    fun generate() : AccessTokenId
}
