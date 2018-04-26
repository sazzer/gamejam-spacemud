package uk.co.grahamcox.space.generation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Unit tests for [Rng]
 */
internal class RngTest {
    /**
     * Test that the same Seed and Name generate the same numbers
     */
    @Test
    fun testRepeatableName() {
        val rng = Rng("seed")

        val rng1 = rng.getRng("name")
        val rng2 = rng.getRng("name")

        val numbers1 = (0..100).map { rng1.nextLong() }
        val numbers2 = (0..100).map { rng2.nextLong() }

        Assertions.assertEquals(numbers1, numbers2)
    }

    /**
     * Test that the same Seed and different Names generate different numbers
     */
    @Test
    fun testDifferentNames() {
        val rng = Rng("seed")

        val rng1 = rng.getRng("name1")
        val rng2 = rng.getRng("name2")

        val numbers1 = (0..100).map { rng1.nextLong() }
        val numbers2 = (0..100).map { rng2.nextLong() }

        Assertions.assertNotEquals(numbers1, numbers2)
    }

    /**
     * Test that the different Seeds and the same Names generate different numbers
     */
    @Test
    fun testDifferentSeeds() {
        val rng1 = Rng("seed1").getRng("name")
        val rng2 = Rng("seed2").getRng("name")

        val numbers1 = (0..100).map { rng1.nextLong() }
        val numbers2 = (0..100).map { rng2.nextLong() }

        Assertions.assertNotEquals(numbers1, numbers2)
    }
}
