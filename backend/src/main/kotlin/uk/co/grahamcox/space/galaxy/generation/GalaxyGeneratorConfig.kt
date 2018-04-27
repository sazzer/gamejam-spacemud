package uk.co.grahamcox.space.galaxy.generation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.space.galaxy.empire.generation.EmpiresGenerator
import uk.co.grahamcox.space.galaxy.generation.rest.GalaxyGeneratorController
import uk.co.grahamcox.space.galaxy.species.generation.SpeciesGeneratorBuilder
import uk.co.grahamcox.space.galaxy.starmap.generation.StarMapGenerator

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
    fun galaxyGeneratorController(galaxyGeneratorBuilder: GalaxyGeneratorBuilder) =
            GalaxyGeneratorController(galaxyGeneratorBuilder)
}
