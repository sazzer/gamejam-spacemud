package uk.co.grahamcox.space.generation.galaxy

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.space.empire.dao.EmpireDao
import uk.co.grahamcox.space.galaxy.dao.GalaxyDao
import uk.co.grahamcox.space.generation.GalaxyPersister
import uk.co.grahamcox.space.generation.empire.EmpiresGenerator
import uk.co.grahamcox.space.generation.galaxy.rest.GalaxyGeneratorController
import uk.co.grahamcox.space.generation.species.SpeciesGeneratorBuilder
import uk.co.grahamcox.space.generation.starmap.StarMapGenerator
import uk.co.grahamcox.space.sector.dao.SectorDao
import uk.co.grahamcox.space.species.dao.SpeciesDao

/**
 * Spring config for Galaxy Generation
 */
@Configuration
class GalaxyGeneratorConfig {
    @Bean
    fun galaxyGenerator(
            starMapGenerator: StarMapGenerator,
            empiresGenerator: EmpiresGenerator,
            speciesGeneratorBuilder: SpeciesGeneratorBuilder
    ) = GalaxyGeneratorBuilder(starMapGenerator, empiresGenerator, speciesGeneratorBuilder)

    @Bean
    fun galaxyPersister(galaxyDao: GalaxyDao,
                        speciesDao: SpeciesDao,
                        sectorDao: SectorDao,
                        empireDao: EmpireDao) = GalaxyPersister(galaxyDao, speciesDao, sectorDao, empireDao)

    @Bean
    fun galaxyGeneratorController(galaxyGeneratorBuilder: GalaxyGeneratorBuilder,
                                  galaxyPersister: GalaxyPersister) =
            GalaxyGeneratorController(galaxyGeneratorBuilder, galaxyPersister)
}
