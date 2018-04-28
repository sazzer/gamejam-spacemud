package uk.co.grahamcox.space.generation

import org.springframework.transaction.annotation.Transactional
import uk.co.grahamcox.space.galaxy.GalaxyData
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.galaxy.dao.GalaxyDao
import uk.co.grahamcox.space.generation.galaxy.Galaxy
import uk.co.grahamcox.space.model.Resource

/**
 * Mechanism to persist a newly generated galaxy
 */
open class GalaxyPersister(
        private val galaxyDao: GalaxyDao
) {
    /**
     * Persist the galaxy to the database
     * @param galaxy The generated galaxy to persist
     * @return The resource details of the galaxy. Note that this does *not* include the persisted details of everything
     * in the galaxy.
     */
    @Transactional
    open fun persistGeneratedGalaxy(galaxy: Galaxy) : Resource<GalaxyId, GalaxyData> {
        val created = galaxyDao.create(GalaxyData(
                name = galaxy.name,
                width = galaxy.width,
                height = galaxy.height))

        return created
    }
}
