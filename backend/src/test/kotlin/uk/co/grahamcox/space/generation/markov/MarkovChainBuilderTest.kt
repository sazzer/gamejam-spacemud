package uk.co.grahamcox.space.generation.markov

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.generation.markov.dao.MarkovChainDao
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Resource
import java.time.Instant

/**
 * Unit tests for [MarkovChainBuilder]
 */
internal class MarkovChainBuilderTest {
    /** The mock DAO */
    private val dao: MarkovChainDao = mockk()

    /** The test subject */
    private val testSubjet = MarkovChainBuilder(dao)

    @Test
    fun buildMarkovChain() {
        val markovChainId = MarkovChainId("00000000-0000-0000-0000-000000000001")
        every { dao.getById(markovChainId) } returns Resource(
                identity = Identity(
                        id = markovChainId,
                        created = Instant.EPOCH,
                        updated = Instant.EPOCH,
                        version = ""
                ),
                data = MarkovChainData(
                        name = "Example",
                        type = "species",
                        prefix = 2,
                        corpus = listOf("ab", "cd", "ef")
                )
        )

        val markovChainGenerator = testSubjet.buildSingleChain(markovChainId)

        Assertions.assertEquals(SimpleMarkovChainGeneratorImpl(listOf("ab", "cd", "ef"), 2), markovChainGenerator)

        verify { dao.getById(markovChainId) }
    }

    @Test
    fun buildUnknownMarkovChain() {
        val markovChainId = MarkovChainId("00000000-0000-0000-0000-000000000001")
        every { dao.getById(markovChainId) } throws ResourceNotFoundException(markovChainId)

        val exception = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubjet.buildSingleChain(markovChainId)
        }

        Assertions.assertEquals(markovChainId, exception.id)
    }
}
