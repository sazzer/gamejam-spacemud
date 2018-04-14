package uk.co.grahamcox.space.rest.hal

import java.time.Instant

/**
 * The meta information about the resource
 * @property created When the resource was created
 * @property updated When the resource was last updated
 * @property version The version of the resource
 */
data class Meta(
        val created: Instant,
        val updated: Instant,
        val version: String
)
