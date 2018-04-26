package uk.co.grahamcox.space.model

/**
 * Representation of a single page of resources
 * @property data The data on the page
 * @property totalCount The total count in the entire system
 */
data class Page<out ID : Id<*>, out DATA>(
        val data: List<Resource<ID, DATA>>,
        val totalCount: Int
)
