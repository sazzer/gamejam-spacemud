package uk.co.grahamcox.space.model

/**
 * Interface describing the ID of some resource
 */
interface Id<out T> {
    /** The actual ID of the resource */
    val id: T
}
