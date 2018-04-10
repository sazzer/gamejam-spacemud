package uk.co.grahamcox.space.model

import java.time.Instant

/**
 * Representation of the Identity of a resource
 * @property id The actual ID
 * @property version The optimistic lock version tag
 * @property created When the resource was created
 * @property updated When the resource was last updated
 */
data class Identity<out ID : Id<*>>(
        val id: ID,
        val version: String,
        val created: Instant,
        val updated: Instant
)
