package uk.co.grahamcox.space.generation

import org.apache.commons.math3.random.RandomGenerator
import org.apache.commons.math3.random.Well19937c
import org.slf4j.LoggerFactory
import java.security.MessageDigest

/**
 * Wrapper around the RNG, allowing it to be seeded and to provide subsets that are seeded keyed off of a name
 */
class Rng(private val seed: String) {
    companion object {
        /** The Logger to use */
        private val LOG = LoggerFactory.getLogger(Rng::class.java)
    }
    /**
     * Get the RNG that has the given name.
     * Every RNG with the same seed and name will generate the same numbers. A different Seed or Name will cause
     * different random numbers to be generated
     * @param name The name of the RNG
     * @return the RNG
     */
    fun getRng(name: String): RandomGenerator = Well19937c(generateSeedArray(name))

    /**
     * Generate the Seed Array to use for the given name
     * @param name The name of the seed array
     * @return the seed array
     */
    private fun generateSeedArray(name: String): IntArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")

        val seedDigest = messageDigest.digest(seed.toByteArray(Charsets.UTF_8))
        val nameDigest = messageDigest.digest(name.toByteArray(Charsets.UTF_8))

        val seedArray = seedDigest.zip(nameDigest)
                .map { it.first * it.second }
                .toIntArray()
        LOG.debug("Seed array for seed {} and name {}: {}", seed, name, seedArray)

        return seedArray
    }
}
