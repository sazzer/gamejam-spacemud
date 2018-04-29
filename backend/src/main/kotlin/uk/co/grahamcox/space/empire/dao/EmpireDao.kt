package uk.co.grahamcox.space.empire.dao

import uk.co.grahamcox.space.empire.EmpireData
import uk.co.grahamcox.space.empire.EmpireId
import uk.co.grahamcox.space.model.Page
import uk.co.grahamcox.space.model.Resource

/**
 * DAO for working with Sectors in an Empire
 */
interface EmpireDao {
    /**
     * Create a new Sector in an Empire
     * @param data The data about the Sector in an Empire to create
     * @return the created Sector in an Empire
     */
    fun create(data: EmpireData) : Resource<EmpireId, EmpireData>

    /**
     * Get a single Sector in an Empire by ID
     * @param id The ID of the Sector in an Empire
     * @return the Sector in an Empire
     */
    fun getById(id: EmpireId) : Resource<EmpireId, EmpireData>

    /**
     * List all of the Sectors in an Empire
     * @return the list of Sectors in an Empire
     */
    fun list() : Page<EmpireId, EmpireData>
}
