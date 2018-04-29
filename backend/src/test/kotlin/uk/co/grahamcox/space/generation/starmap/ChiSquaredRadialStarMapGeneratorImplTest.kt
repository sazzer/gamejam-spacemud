package uk.co.grahamcox.space.generation.starmap

import org.apache.commons.math3.random.Well19937a
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * Tests for the [ChiSquaredRadialStarMapGeneratorImpl]
 */
@Disabled
internal class ChiSquaredRadialStarMapGeneratorImplTest {
    /**
     * Generate a galaxy and render it to a file
     */
    @Test
    fun generate() {
        val testSubject = ChiSquaredRadialStarMapGeneratorImpl()

        val galaxy = testSubject.generateGalaxy(Well19937a(), 100, 1000000)
        val galaxyFilename = "/tmp/galaxy-chisquared.png"

        galaxy.render(galaxyFilename)
    }

}
