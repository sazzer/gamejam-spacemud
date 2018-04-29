package uk.co.grahamcox.space.species.dao

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.species.SpeciesData
import uk.co.grahamcox.space.species.SpeciesId
import uk.co.grahamcox.space.species.SpeciesTraits
import uk.co.grahamcox.space.spring.SpringTestBase
import java.time.Instant

/**
 * Tests for the [JdbcSpeciesDaoImpl]
 */
@Sql("classpath:uk/co/grahamcox/space/species/dao/data.sql")
internal class JdbcSpeciesDaoImplTest : SpringTestBase() {
    /** ID that doesn't exist */
    private val NON_EXISTANT_ID = SpeciesId("00000000-0000-0000-0000-000000000000")
    /** ID that does exist */
    private val EXISTING_ID = SpeciesId("00000000-0000-0000-0000-000000000001")
    /** Galaxy ID to use */
    private val GALAXY_ID = GalaxyId("00000000-0000-0000-0000-000000000001")

    @Autowired
    private lateinit var testSubject: SpeciesDao

    @Test
    fun getUnknownById() {
        val e = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubject.getById(NON_EXISTANT_ID)
        }

        Assertions.assertEquals(NON_EXISTANT_ID, e.id)
    }

    @Test
    fun getKnownById() {
        val species = testSubject.getById(EXISTING_ID)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(EXISTING_ID, species.identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", species.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), species.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), species.identity.updated) },
                Executable { Assertions.assertEquals("Example Species", species.data.name) },
                Executable { Assertions.assertEquals(GALAXY_ID, species.data.galaxyId) },
                Executable { Assertions.assertEquals(mapOf(SpeciesTraits.AGGRESSIVE to 2, SpeciesTraits.CREATIVE to -1), species.data.traits) }
        )
    }

    @Test
    fun listSpecies() {
        val page = testSubject.list()

        Assertions.assertAll(
                Executable { Assertions.assertEquals(2, page.totalCount) },

                Executable { Assertions.assertEquals(2, page.data.size) },

                Executable { Assertions.assertEquals(EXISTING_ID, page.data[0].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[0].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[0].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[0].identity.updated) },
                Executable { Assertions.assertEquals("Example Species", page.data[0].data.name) },
                Executable { Assertions.assertEquals(GALAXY_ID, page.data[0].data.galaxyId) },
                Executable { Assertions.assertEquals(mapOf(SpeciesTraits.AGGRESSIVE to 2, SpeciesTraits.CREATIVE to -1), page.data[0].data.traits) },

                Executable { Assertions.assertEquals(SpeciesId("00000000-0000-0000-0000-000000000002"), page.data[1].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[1].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[1].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[1].identity.updated) },
                Executable { Assertions.assertEquals("Second Species", page.data[1].data.name) },
                Executable { Assertions.assertEquals(GALAXY_ID, page.data[1].data.galaxyId) },
                        Executable { Assertions.assertEquals(emptyMap<SpeciesTraits, Int>(), page.data[1].data.traits) }
        )
    }

    @Test
    fun createSpecies() {
        val created = testSubject.create(SpeciesData(
                name = "New Species",
                galaxyId = GALAXY_ID,
                traits = mapOf(SpeciesTraits.EXPANSIVE to 2, SpeciesTraits.INDUSTRIOUS to -1)
        ))

        Assertions.assertAll(
                Executable { Assertions.assertNotNull(created.identity.id) },
                Executable { Assertions.assertNotNull(created.identity.version) },
                Executable { Assertions.assertNotNull(created.identity.created) },
                Executable { Assertions.assertNotNull(created.identity.updated) },
                Executable { Assertions.assertEquals("New Species", created.data.name) },
                Executable { Assertions.assertEquals(GALAXY_ID, created.data.galaxyId) },
                Executable { Assertions.assertEquals(mapOf(SpeciesTraits.EXPANSIVE to 2, SpeciesTraits.INDUSTRIOUS to -1), created.data.traits) }
        )

        val loaded = testSubject.getById(created.identity.id)

        Assertions.assertEquals(created, loaded)
    }

    @Test
    fun createDuplicateName() {
        Assertions.assertThrows(DuplicateNameException::class.java) {
            testSubject.create(SpeciesData(
                    name = "Second Species",
                    galaxyId = GALAXY_ID,
                    traits = mapOf()
            ))
        }
    }

}
