package uk.co.grahamcox.space.generation.markov.dao

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.generation.markov.MarkovChainData
import uk.co.grahamcox.space.generation.markov.MarkovChainId
import uk.co.grahamcox.space.spring.SpringTestBase
import java.time.Instant

/**
 * Tests for the [JdbcMarkovChainDaoImpl]
 */
@Sql("classpath:uk/co/grahamcox/space/generation/markov/dao/data.sql")
internal class JdbcMarkovChainDaoImplTest : SpringTestBase() {
    /** ID that doesn't exist */
    private val NON_EXISTANT_ID = MarkovChainId("00000000-0000-0000-0000-000000000000")
    /** ID that does exist */
    private val EXISTING_ID = MarkovChainId("00000000-0000-0000-0000-000000000001")

    /** The test subject */
    @Autowired
    private lateinit var testSubject: MarkovChainDao

    /**
     * Test listing all of the markov chains
     */
    @Test
    fun listMarkovChains() {
        val page = testSubject.list()

        Assertions.assertAll(
                Executable { Assertions.assertEquals(2, page.totalCount) },

                Executable { Assertions.assertEquals(EXISTING_ID, page.data[0].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[0].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[0].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[0].identity.updated) },
                Executable { Assertions.assertEquals("Example Markov Chain", page.data[0].data.name) },
                Executable { Assertions.assertEquals("species", page.data[0].data.type) },
                Executable { Assertions.assertEquals(2, page.data[0].data.prefix) },
                Executable { Assertions.assertEquals(listOf("ab", "bc", "cd"), page.data[0].data.corpus) },

                Executable { Assertions.assertEquals(MarkovChainId("00000000-0000-0000-0000-000000000002"), page.data[1].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[1].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[1].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[1].identity.updated) },
                Executable { Assertions.assertEquals("Second Markov Chain", page.data[1].data.name) },
                Executable { Assertions.assertEquals("species", page.data[1].data.type) },
                Executable { Assertions.assertEquals(2, page.data[1].data.prefix) },
                Executable { Assertions.assertEquals(listOf("ab", "bc", "cd"), page.data[1].data.corpus) }
        )

    }
    /**
     * Test getting an unknown Markov Chain by ID
     */
    @Test
    fun getUnknownMarkovChainById() {
        val e = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubject.getById(NON_EXISTANT_ID)
        }

        Assertions.assertEquals(NON_EXISTANT_ID, e.id)
    }

    /**
     * Test getting a Markov Chain by ID
     */
    @Test
    fun getMarkovChainById() {
        val markovChain = testSubject.getById(EXISTING_ID)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(EXISTING_ID, markovChain.identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", markovChain.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), markovChain.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), markovChain.identity.updated) },
                Executable { Assertions.assertEquals("Example Markov Chain", markovChain.data.name) },
                Executable { Assertions.assertEquals("species", markovChain.data.type) },
                Executable { Assertions.assertEquals(2, markovChain.data.prefix) },
                Executable { Assertions.assertEquals(listOf("ab", "bc", "cd"), markovChain.data.corpus) }
        )
    }

    /**
     * Test that deleting an ID that doesn't exist will not fail
     */
    @Test
    fun deleteUnknownMarkovChainById() {
        testSubject.delete(NON_EXISTANT_ID)
    }

    /**
     * Test that deleting an ID that does exist works correctly
     */
    @Test
    fun deleteKnownMarkovChainById() {
        val countBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM markovchains WHERE markov_chain_id = :id::uuid",
                mapOf("id" to EXISTING_ID.id),
                Int::class.java)

        Assertions.assertEquals(1, countBefore)

        testSubject.delete(EXISTING_ID)

        val countAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM markovchains WHERE markov_chain_id = :id::uuid",
                mapOf("id" to EXISTING_ID.id),
                Int::class.java)

        Assertions.assertEquals(0, countAfter)
    }

    /**
     * Test creating a new Markov Chain
     */
    @Test
    fun createMarkovChain() {
        val created = testSubject.create(MarkovChainData(
                name = "New Chain",
                type = "planet",
                prefix = 3,
                corpus = listOf("abc", "def", "ghi")
        ))

        Assertions.assertAll(
                Executable { Assertions.assertNotNull(created.identity.id) },
                Executable { Assertions.assertNotNull(created.identity.version) },
                Executable { Assertions.assertNotNull(created.identity.created) },
                Executable { Assertions.assertNotNull(created.identity.updated) },
                Executable { Assertions.assertEquals("New Chain", created.data.name) },
                Executable { Assertions.assertEquals("planet", created.data.type) },
                Executable { Assertions.assertEquals(3, created.data.prefix) },
                Executable { Assertions.assertEquals(listOf("abc", "def", "ghi"), created.data.corpus) }
        )

        val loaded = testSubject.getById(created.identity.id)

        Assertions.assertEquals(created, loaded)
    }

    /**
     * Test creating a Markov Chain with a duplicate name
     */
    @Test
    fun createDuplicateName() {
        Assertions.assertThrows(DuplicateNameException::class.java) {
            testSubject.create(MarkovChainData(
                    name = "Example Markov Chain",
                    type = "planet",
                    prefix = 3,
                    corpus = listOf("abc", "def", "ghi")
            ))
        }
    }

    /**
     * Test updating an existing Markov Chain
     */
    @Test
    fun updateMarkovChain() {
        val created = testSubject.update(EXISTING_ID, MarkovChainData(
                name = "New Chain",
                type = "planet",
                prefix = 3,
                corpus = listOf("abc", "def", "ghi")
        ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(EXISTING_ID, created.identity.id) },
                Executable { Assertions.assertNotEquals("11111111-1111-1111-1111-111111111111", created.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), created.identity.created) },
                Executable { Assertions.assertNotEquals(Instant.parse("2018-03-28T09:29:00Z"), created.identity.updated) },
                Executable { Assertions.assertEquals("New Chain", created.data.name) },
                Executable { Assertions.assertEquals("planet", created.data.type) },
                Executable { Assertions.assertEquals(3, created.data.prefix) },
                Executable { Assertions.assertEquals(listOf("abc", "def", "ghi"), created.data.corpus) }
        )

        val loaded = testSubject.getById(created.identity.id)

        Assertions.assertEquals(created, loaded)
    }

    /**
     * Test updating a non-existing Markov Chain
     */
    @Test
    fun updateUnknownMarkovChain() {
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubject.update(NON_EXISTANT_ID, MarkovChainData(
                    name = "New Chain",
                    type = "planet",
                    prefix = 3,
                    corpus = listOf("abc", "def", "ghi")
            ))
        }
    }

    /**
     * Test updating a Markov Chain to give it a duplicate name
     */
    @Test
    fun updateMarkovChainDuplicateName() {
        Assertions.assertThrows(DuplicateNameException::class.java) {
            testSubject.update(EXISTING_ID, MarkovChainData(
                    name = "Second Markov Chain",
                    type = "planet",
                    prefix = 3,
                    corpus = listOf("abc", "def", "ghi")
            ))
        }
    }
}
