package uk.co.grahamcox.space.generation.empire

import org.apache.commons.math3.random.Well19937c
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.species.Species
import uk.co.grahamcox.space.species.SpeciesTraits

/**
 * Tests for the [EmpireSizeGenerator]
 */
internal class EmpireSizeGeneratorTest {
    /**
     * Test the generator
     */
    @Test
    fun test() {
        val testSizeGenerator = EmpireSizeGenerator()
        for (i in -3..3) {
            testSizeGenerator.generate(Well19937c(),
                    Species("Testing $i", mapOf(SpeciesTraits.EXPANSIVE to i)),
                    10000)
        }
    }
}
