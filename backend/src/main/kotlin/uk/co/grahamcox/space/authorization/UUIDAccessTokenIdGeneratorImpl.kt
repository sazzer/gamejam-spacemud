package uk.co.grahamcox.space.authorization

import java.util.*

/**
 * Access Token ID Generator that generates UUIDs
 */
class UUIDAccessTokenIdGeneratorImpl : AccessTokenIdGenerator {
    /**
     * Generate an ID for an Access Token
     */
    override fun generate() = AccessTokenId(UUID.randomUUID().toString())
}
