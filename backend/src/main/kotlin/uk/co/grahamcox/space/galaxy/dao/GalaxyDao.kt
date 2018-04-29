package uk.co.grahamcox.space.galaxy.dao

import uk.co.grahamcox.space.galaxy.GalaxyData
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource

/**
 * DAO for working with Galaxies
 */
interface GalaxyDao {
    /**
     * Create a new Galaxy
     * @param data The data about the galaxy to create
     * @return the created galaxy
     */
    fun create(data: GalaxyData) : Resource<GalaxyId, GalaxyData>

    /**
     * Get a single Galaxy by ID
     * @param id The ID of the galaxy
     * @return the galaxy
     */
    fun getById(id: GalaxyId) : Resource<GalaxyId, GalaxyData>

    /**
     * List all of the galaxies
     * @return the list of galaxies
     */
    fun list() : Page<GalaxyId, GalaxyData>
}
