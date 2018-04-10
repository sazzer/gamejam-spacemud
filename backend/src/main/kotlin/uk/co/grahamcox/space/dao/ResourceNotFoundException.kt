package uk.co.grahamcox.space.dao

import uk.co.grahamcox.space.model.Id

/**
 * Exception to indicate that a resource loaded from the database was not found
 * @property id The ID of the missing resource
 */
class ResourceNotFoundException(val id: Id<*>) : RuntimeException("Resource not found: $id")
