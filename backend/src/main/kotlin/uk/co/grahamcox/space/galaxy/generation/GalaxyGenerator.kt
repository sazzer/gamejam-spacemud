package uk.co.grahamcox.space.galaxy.generation

import org.apache.commons.math3.distribution.NormalDistribution
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.galaxy.Galaxy
import uk.co.grahamcox.space.galaxy.empire.generation.EmpiresGenerator
import uk.co.grahamcox.space.galaxy.species.generation.SpeciesGenerator
import uk.co.grahamcox.space.galaxy.starmap.generation.StarMapGenerator
import uk.co.grahamcox.space.generation.Rng
import kotlin.math.roundToInt

/**
 * Generator to generate an entire galaxy
 */
class GalaxyGenerator(
        private val starMapGenerator: StarMapGenerator,
        private val speciesGenerator: SpeciesGenerator,
        private val empiresGenerator: EmpiresGenerator
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(GalaxyGenerator::class.java)
    }

    /**
     * Generate the galaxy
     * @param rng The RNG to use for the generation
     * @param size The size of the galaxy
     * @param numSpecies The number of species in the galaxy
     */
    fun generate(rng: Rng, size: Int, stars: Int, numSpecies: Int): Galaxy {
        val galaxyRng = rng.getRng("Galaxy")

        val sizeDistribution = NormalDistribution(galaxyRng, size.toDouble(), size.toDouble() / 20)
        val realSize = sizeDistribution.sample().roundToInt()

        val starsDistribution = NormalDistribution(galaxyRng, stars.toDouble(), stars.toDouble() / 10)
        val realStars = starsDistribution.sample().roundToInt()

        val speciesDistribution = NormalDistribution(galaxyRng, numSpecies.toDouble(), 1.0)
        val realNumSpecies = speciesDistribution.sample().roundToInt()

        LOG.debug("Generating a galaxy of size {}, with {} stars and {} species",
                realSize, realStars, realNumSpecies)

        val starMap = starMapGenerator.generateGalaxy(rng.getRng("StarMap"), realSize, realStars)
        val species = (0 until realNumSpecies).map {
            speciesGenerator.generate(rng.getRng("Species $it"))
        }
        val empires = empiresGenerator.generate(rng.getRng("Empires"), starMap, species)

        return Galaxy(starMap, empires)
    }
}
