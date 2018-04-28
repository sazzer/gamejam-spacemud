package uk.co.grahamcox.space.generation

import org.springframework.transaction.annotation.Transactional
import uk.co.grahamcox.space.empire.EmpireData
import uk.co.grahamcox.space.empire.dao.EmpireDao
import uk.co.grahamcox.space.galaxy.Coords
import uk.co.grahamcox.space.galaxy.GalaxyData
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.galaxy.dao.GalaxyDao
import uk.co.grahamcox.space.generation.galaxy.Galaxy
import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.sector.SectorData
import uk.co.grahamcox.space.sector.dao.SectorDao
import uk.co.grahamcox.space.species.SpeciesData
import uk.co.grahamcox.space.species.SpeciesTraits
import uk.co.grahamcox.space.species.dao.SpeciesDao

/**
 * Mechanism to persist a newly generated galaxy
 */
open class GalaxyPersister(
        private val galaxyDao: GalaxyDao,
        private val speciesDao: SpeciesDao,
        private val sectorDao: SectorDao,
        private val empireDao: EmpireDao
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

        val speciesIds = galaxy.species.map { species ->
            val created = speciesDao.create(SpeciesData(
                    name = species.name,
                    traits = SpeciesTraits.values().map { trait -> trait to species.getTrait(trait) }
                            .filter { it.second != 0 }
                            .toMap(),
                    galaxyId = created.identity.id
            ))

            species to created.identity.id
        }.toMap()

        for (x in 0 until galaxy.width) {
            for (y in 0 until galaxy.height) {
                val coords = Coords(x, y)
                val sector = galaxy.getSector(coords)
                val sectorRecord = sectorDao.create(SectorData(
                        galaxy = created.identity.id,
                        x = x,
                        y = y,
                        stars = sector.totalCount
                ))

                sector.speciesCount.filterValues { it > 0 }
                        .forEach { species, count ->
                            val speciesId = speciesIds[species]!!

                            empireDao.create(EmpireData(
                                    species = speciesId,
                                    sector = sectorRecord.identity.id,
                                    stars = count
                            ))
                        }
            }
        }
        return created
    }
}
