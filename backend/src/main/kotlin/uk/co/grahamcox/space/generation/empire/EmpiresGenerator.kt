package uk.co.grahamcox.space.generation.empire

import org.apache.commons.math3.random.RandomGenerator
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.galaxy.Coords
import uk.co.grahamcox.space.galaxy.empire.Empire
import uk.co.grahamcox.space.galaxy.empire.MutableEmpire
import uk.co.grahamcox.space.galaxy.species.Species
import uk.co.grahamcox.space.galaxy.species.SpeciesTraits
import uk.co.grahamcox.space.galaxy.starmap.StarMap

/**
 * Generate the empires for a set of species
 */
class EmpiresGenerator(
        private val empireSizeGenerator: EmpireSizeGenerator,
        private val empireSeedGenerator: EmpireSeedGenerator
) {
    companion object {
        /** the logger to use */
        private val LOG = LoggerFactory.getLogger(EmpiresGenerator::class.java)
    }
    /**
     * Actually generate the empires for the given speciesList
     * @param rng The RNG to use
     * @param starMap The star map to populate
     * @param speciesList The species to generate empires for
     * @param populationDensity The population density to generate
     * @return the map of Species to their Empires
     */
    fun generate(rng: RandomGenerator, starMap: StarMap, speciesList: List<Species>, populationDensity: Double) : Map<Species, Empire> {
        // Firstly we generate a whole bunch of empty empires
        val empireMaps = speciesList.map { species ->
            val empireStarMap = Array(starMap.width) {
                IntArray(starMap.height) {
                    0
                }
            }

            species to MutableEmpire(empireStarMap)
        }.toMap()

        // Then we generate the size of each empire
        // We need the number of stars in the entire galaxy for this, then it's a percentage of that shared between each
        // species
        val totalStars = (0 until starMap.width).fold(0) { acc, x ->
            (0 until starMap.height).fold(acc) { acc1, y ->
                acc1 + starMap.getSector(Coords(x, y))
            }
        }
        LOG.trace("Total number of stars: {}", totalStars)

        val starsPerSpecies = ((totalStars * populationDensity) / speciesList.size).toInt()
        LOG.trace("Average number of stars per species: {}", starsPerSpecies)

        val sizes = speciesList.map { species ->
            val size = empireSizeGenerator.generate(rng, species, starsPerSpecies)

            LOG.debug("Species {} has empire of size {}", species, size)
            species to size
        }.toMap().toMutableMap()

        // Then we generate the seed for each speciesList.
        speciesList.forEach { species ->
            val seed = empireSeedGenerator.generateEmpireSeed(rng, starMap, empireMaps.values)
            val empire = empireMaps[species]!!
            empire.setSector(seed, 1)

            LOG.debug("Species {} has empire seed {}", species, seed)
        }

        // Then we generate the actual empires
        // Keep going all the while we still have any remaining empire size to allocate
        while (sizes.isNotEmpty()) {
            // Place one star per species
            sizes.forEach { species, size ->
                val myEmpire = empireMaps[species]!!

                // Randomly pick a sector that already has a star from this species
                val expandFrom = pickSector(rng, myEmpire)
                LOG.trace("Expanding species {} from {}", species, expandFrom)

                // Build the list of sectors that we could pick from, based on this one
                val expandToList = mutableListOf<Coords>()
                expandToList += buildUnpopulatedList(expandFrom, starMap, empireMaps.values)
                if (species.getTrait(SpeciesTraits.EXPANSIVE) < 0) {
                    expandToList += buildUnpopulatedList(expandFrom, starMap, empireMaps.values)
                }

                for (dx in -1..1) {
                    for (dy in -1..1) {
                        val expandTo = Coords(expandFrom.x + dx, expandFrom.y + dy)
                        if (expandTo.x >= 0 && expandTo.x < starMap.width
                                && expandTo.y >= 0 && expandTo.y < starMap.height) {
                            // If we are expansive, add every surrounding sector
                            if (species.getTrait(SpeciesTraits.EXPANSIVE) > 0) {
                                expandToList += buildUnpopulatedList(expandTo, starMap, empireMaps.values)
                            }

                            val otherEmpires = (empireMaps.values - myEmpire)
                            val populated = isPopulated(expandTo, otherEmpires)
                            // If we are aggressive, add every populated surrounding sector
                            if (species.getTrait(SpeciesTraits.AGGRESSIVE) > 0 && populated) {
                                expandToList += buildUnpopulatedList(expandTo, starMap, empireMaps.values)
                            }
                            // Add every surrounding sector if we're not unaggressive OR it's not populated
                            // I.e. if we are unaggressive, we skip populated sectors
                            if (species.getTrait(SpeciesTraits.AGGRESSIVE) >= 0 || !populated) {
                                expandToList += buildUnpopulatedList(expandTo, starMap, empireMaps.values)
                            }
                        }
                    }
                }

                // Randomly pick one of those sectors
                LOG.trace("Expanding to one of {}. Total number of options: {}",
                        expandToList.toSet(), expandToList.size)
                if (expandToList.isNotEmpty()) {
                    val expandTo = expandToList[rng.nextInt(expandToList.size)]
                    LOG.trace("Species {} expanding into {}", species, expandTo)
                    myEmpire.setSector(expandTo, myEmpire.getSector(expandTo) + 1)
                } else {
                    LOG.trace("Failed to generate any viable sectors to expand into")
                }
            }

            speciesList.forEach { species ->
                // Now we decrement the number of stars for each species by one, removing it from the map if we've
                // reached 0. This needs to be done separately to avoid concurrent modification errors
                val currentSize = sizes[species]
                if (currentSize != null) {
                    if (currentSize > 1) {
                        sizes[species] = currentSize - 1
                    } else {
                        sizes.remove(species)
                        LOG.debug("Allocated all stars to species {}", species)
                    }
                }
            }
        }

        return empireMaps
    }

    /**
     * Helper to see if any of the empires listed have already expanded into this sector
     * @param expandTo The coords of the sector
     * @param otherEmpires The empires to check
     * @return true if any of the other empires have at least one star in this sector
     */
    private fun isPopulated(expandTo: Coords, otherEmpires: List<Empire>): Boolean {
        return otherEmpires.any { it.getSector(expandTo) > 0 }
    }

    /**
     * Build a list of Coords objects with one entry for each unpopulated star in that sector
     * @param coords The coords of the sector
     * @param starMap The overall star map
     * @param empires The list of empires
     * @return a list of coords, with one entry for each unpopulated star
     */
    fun buildUnpopulatedList(coords: Coords, starMap: StarMap, empires: Collection<Empire>) : List<Coords> {
        val totalStars = starMap.getSector(coords)
        val populatedStars = empires.map { it.getSector(coords) }.sum()
        val unpopulatedStars = totalStars - populatedStars
        LOG.trace("Sector {} has {} total stars, {} populated stars and thus {} unpopulated stars",
                coords, totalStars, populatedStars, unpopulatedStars)

        return (0 until unpopulatedStars).map { coords }
    }

    /**
     * Pick a random sector in the empire that is populated
     * @param rng The RNG to use
     * @param empire The empire to pick from
     * @return the randomly selected sector
     */
    private fun pickSector(rng: RandomGenerator, empire: Empire): Coords {
        val sectorList = mutableListOf<Coords>()

        for (x in 0 until empire.width) {
            for (y in 0 until empire.height) {
                val coords = Coords(x, y)
                if (empire.getSector(coords) > 0) {
                    sectorList.add(coords)
                }
            }
        }

        val count = rng.nextInt(sectorList.size)
        return sectorList[count]
    }
}
