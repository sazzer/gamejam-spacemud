package uk.co.grahamcox.space.galaxy.species.generation

import io.mockk.every
import io.mockk.mockk
import org.apache.commons.math3.random.Well19937c
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.generation.markov.MarkovChainGenerator

/**
 * Unit tests for [SpeciesGenerator]
 */
internal class SpeciesGeneratorTest {
    @Test
    fun test() {
        val rng = Well19937c()

        val nameGenerator = mockk<MarkovChainGenerator>()
        every { nameGenerator.generate(rng) } returns "Species x"

        val testSubject = SpeciesGenerator(nameGenerator = nameGenerator, traitShift = 10)

        val species = testSubject.generate(rng)
        System.out.println(species)

        // Net sum of all traits should be 0
        Assertions.assertEquals(0, species.traits.values.sum())
    }

    @Test
    fun testLots() {
        val rng = Well19937c()

        val nameGenerator = mockk<MarkovChainGenerator>()
        every { nameGenerator.generate(rng) } returns "Species x"

        val testSubject = SpeciesGenerator(nameGenerator = nameGenerator, traitShift = 10)

        for (i in 0 until 10) {
            val species = testSubject.generate(rng)
            System.out.println(species)
        }
    }
}
