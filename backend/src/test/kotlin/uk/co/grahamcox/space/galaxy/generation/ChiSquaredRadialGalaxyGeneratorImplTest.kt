package uk.co.grahamcox.space.galaxy.generation

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * Tests for the [ChiSquaredRadialGalaxyGeneratorImpl]
 */
@Disabled
internal class ChiSquaredRadialGalaxyGeneratorImplTest : GalaxyGeneratorTestBase() {
    /**
     * Generate a galaxy and render it to a file
     */
    @Test
    fun generate() {
        val testSubject = ChiSquaredRadialGalaxyGeneratorImpl()

        val galaxy = testSubject.generateGalaxy(100, 1000000)
        val galaxyFilename = "/tmp/galaxy-chisquared.png"

        renderGalaxy(galaxy, galaxyFilename)
    }

}
