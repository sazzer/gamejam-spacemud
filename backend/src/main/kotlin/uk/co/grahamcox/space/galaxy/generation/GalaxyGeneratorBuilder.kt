package uk.co.grahamcox.space.galaxy.generation

import uk.co.grahamcox.space.galaxy.empire.generation.EmpiresGenerator
import uk.co.grahamcox.space.galaxy.species.generation.SpeciesGeneratorBuilder
import uk.co.grahamcox.space.galaxy.starmap.generation.StarMapGenerator

/**
 * Builder to get the Galaxy Generator to use, loading it's configuration from the database as needed
 * @property starMapGenerator The star map generator to use
 * @property empiresGenerator The empires generator to use
 * @property speciesGeneratorBuilder The means to build the Species Generator to use
 */
class GalaxyGeneratorBuilder(
        private val starMapGenerator: StarMapGenerator,
        private val empiresGenerator: EmpiresGenerator,
        private val speciesGeneratorBuilder: SpeciesGeneratorBuilder
) {
    /**
     * Build the Galaxy Generator
     * @return the Galaxy Generator
     */
    fun build() : GalaxyGenerator {
        return GalaxyGenerator(
                starMapGenerator,
                speciesGeneratorBuilder.build(),
                empiresGenerator
        )
    }
}
