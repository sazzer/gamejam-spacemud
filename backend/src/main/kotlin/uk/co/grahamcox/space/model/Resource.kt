package uk.co.grahamcox.space.model

/**
 * Representation of an actual resource in the system
 * @property identity The identity of the resource
 * @property data The data for the resource
 */
data class Resource<out ID : Id<*>, out DATA>(
        val identity: Identity<ID>,
        val data: DATA
)
