package uk.co.grahamcox.space.galaxy.generation.rest

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.space.galaxy.generation.GalaxyGeneratorBuilder

/**
 * Controller to support creation of an entire galaxy
 */
@RestController
@RequestMapping(value = ["/api/generation/galaxies"], produces = ["application/hal+json"])
class GalaxyGeneratorController(
        private val galaxyGeneratorBuilder: GalaxyGeneratorBuilder
) {
    /**
     * Actually generate a new galaxy and persist it to the database
     * @param input The input details that describe the galaxy to generate
     */
    @RequestMapping(method = [RequestMethod.POST])
    fun generateGalaxy(@RequestBody input: GalaxyGenerationInputModel) {
        val galaxyGenerator = galaxyGeneratorBuilder.build()

        val galaxy = galaxyGenerator.generate(input.name,
                input.requestedSize,
                input.requestedStars,
                input.requestedSpecies,
                input.populationDensity)

        TODO()
    }
}
