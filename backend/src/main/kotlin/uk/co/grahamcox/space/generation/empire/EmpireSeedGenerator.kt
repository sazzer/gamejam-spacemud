package uk.co.grahamcox.space.generation.empire

import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.galaxy.Coords
import uk.co.grahamcox.space.generation.starmap.StarMap

/**
 * Generate the sector that an empire should start in
 */
class EmpireSeedGenerator {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(EmpireSeedGenerator::class.java)
    }

    /**
     * Generate the seed co-ordinates for the empire
     * @param rng The RNG to use
     * @param starMap The star map to populate
     * @param others The other empires already in the galaxy, to avoid
     */
    fun generateEmpireSeed(rng: RandomGenerator, starMap: StarMap, others: Collection<Empire>): Coords {
        var result: Coords

        // Note that we loop all the while the randomly generated sector either has no stars, or else has at least
        // one other empire present
        do {
            val x = rng.nextInt(starMap.width)
            val y = rng.nextInt(starMap.height)

            result = Coords(x, y)
            LOG.trace("Generated coords: {}", result)
        } while (!(starMap.getSector(result) > 0 && others.all { it.getSector(result) == 0 }))

        return result
    }
}
