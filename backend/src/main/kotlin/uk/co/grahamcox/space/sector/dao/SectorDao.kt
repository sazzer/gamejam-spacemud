package uk.co.grahamcox.space.sector.dao

import uk.co.grahamcox.space.sector.SectorData
import uk.co.grahamcox.space.sector.SectorId
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource

/**
 * DAO for working with Sectors
 */
interface SectorDao {
    /**
     * Create a new Sector
     * @param data The data about the sector to create
     * @return the created sector
     */
    fun create(data: SectorData) : Resource<SectorId, SectorData>

    /**
     * Get a single Sector by ID
     * @param id The ID of the sector
     * @return the sector
     */
    fun getById(id: SectorId) : Resource<SectorId, SectorData>

    /**
     * List all of the sectors
     * @return the list of sectors
     */
    fun list() : Page<SectorId, SectorData>
}
