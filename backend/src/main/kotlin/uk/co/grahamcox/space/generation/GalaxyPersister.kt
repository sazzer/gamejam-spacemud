package uk.co.grahamcox.space.generation

import org.springframework.transaction.annotation.Transactional
import uk.co.grahamcox.space.galaxy.GalaxyData
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.galaxy.dao.GalaxyDao
import uk.co.grahamcox.space.generation.galaxy.Galaxy
import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.species.SpeciesData
import uk.co.grahamcox.space.species.SpeciesTraits
import uk.co.grahamcox.space.species.dao.SpeciesDao

/**
 * Mechanism to persist a newly generated galaxy
 */
open class GalaxyPersister(
        private val galaxyDao: GalaxyDao,
        private val speciesDao: SpeciesDao
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

        galaxy.species.map { species -> SpeciesData(
                name = species.name,
                traits = SpeciesTraits.values().map { trait -> trait to species.getTrait(trait) }
                        .filter { it.second != 0 }
                        .toMap(),
                galaxyId = created.identity.id
        )}.forEach { species -> speciesDao.create(species) }

        return created
    }
}
