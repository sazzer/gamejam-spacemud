package uk.co.grahamcox.space.species.dao

import uk.co.grahamcox.space.species.SpeciesData
import uk.co.grahamcox.space.species.SpeciesId
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource

/**
 * DAO for working with Species
 */
interface SpeciesDao {
    /**
     * Create a new Species
     * @param data The data about the species to create
     * @return the created species
     */
    fun create(data: SpeciesData) : Resource<SpeciesId, SpeciesData>

    /**
     * Get a single Species by ID
     * @param id The ID of the species
     * @return the species
     */
    fun getById(id: SpeciesId) : Resource<SpeciesId, SpeciesData>

    /**
     * List all of the Species
     * @return the list of Species
     */
    fun list() : Page<SpeciesId, SpeciesData>
}
