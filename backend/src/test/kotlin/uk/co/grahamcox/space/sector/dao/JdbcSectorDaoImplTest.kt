package uk.co.grahamcox.space.sector.dao

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.sector.SectorData
import uk.co.grahamcox.space.sector.SectorId
import uk.co.grahamcox.space.spring.SpringTestBase
import java.time.Instant

/**
 * Tests for the [JdbcSectorDaoImpl]
 */
@Sql("classpath:uk/co/grahamcox/space/sector/dao/data.sql")
internal class JdbcSectorDaoImplTest : SpringTestBase() {
    /** ID that doesn't exist */
    private val NON_EXISTANT_ID = SectorId("00000000-0000-0000-0000-000000000000")
    /** ID that does exist */
    private val EXISTING_ID = SectorId("00000000-0000-0000-0000-000000000001")

    /** ID of the Galaxy */
    private val GALAXY_ID = GalaxyId("00000000-0000-0000-0000-000000000001")

    @Autowired
    private lateinit var testSubject: SectorDao

    @Test
    fun getUnknownById() {
        val e = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubject.getById(NON_EXISTANT_ID)
        }

        Assertions.assertEquals(NON_EXISTANT_ID, e.id)
    }

    @Test
    fun getKnownById() {
        val sector = testSubject.getById(EXISTING_ID)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(EXISTING_ID, sector.identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", sector.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), sector.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), sector.identity.updated) },
                Executable { Assertions.assertEquals(GALAXY_ID, sector.data.galaxy) },
                Executable { Assertions.assertEquals(0, sector.data.x) },
                Executable { Assertions.assertEquals(0, sector.data.y) },
                Executable { Assertions.assertEquals(1, sector.data.stars) }
        )
    }

    @Test
    fun listSectors() {
        val page = testSubject.list()

        Assertions.assertAll(
                Executable { Assertions.assertEquals(2, page.totalCount) },

                Executable { Assertions.assertEquals(2, page.data.size) },

                Executable { Assertions.assertEquals(EXISTING_ID, page.data[0].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[0].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[0].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[0].identity.updated) },
                Executable { Assertions.assertEquals(GALAXY_ID, page.data[0].data.galaxy) },
                Executable { Assertions.assertEquals(0, page.data[0].data.x) },
                Executable { Assertions.assertEquals(0, page.data[0].data.y) },
                Executable { Assertions.assertEquals(1, page.data[0].data.stars) },

                Executable { Assertions.assertEquals(SectorId("00000000-0000-0000-0000-000000000002"), page.data[1].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[1].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[1].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[1].identity.updated) },
                Executable { Assertions.assertEquals(GALAXY_ID, page.data[1].data.galaxy) },
                Executable { Assertions.assertEquals(0, page.data[1].data.x) },
                Executable { Assertions.assertEquals(1, page.data[1].data.y) },
                Executable { Assertions.assertEquals(2, page.data[1].data.stars) }
        )
    }

    @Test
    fun createSector() {
        val created = testSubject.create(SectorData(
                galaxy = GALAXY_ID,
                x = 1,
                y = 1,
                stars = 5
        ))

        Assertions.assertAll(
                Executable { Assertions.assertNotNull(created.identity.id) },
                Executable { Assertions.assertNotNull(created.identity.version) },
                Executable { Assertions.assertNotNull(created.identity.created) },
                Executable { Assertions.assertNotNull(created.identity.updated) },
                Executable { Assertions.assertEquals(GALAXY_ID, created.data.galaxy) },
                Executable { Assertions.assertEquals(1, created.data.x) },
                Executable { Assertions.assertEquals(1, created.data.y) },
                Executable { Assertions.assertEquals(5, created.data.stars) }
        )

        val loaded = testSubject.getById(created.identity.id)

        Assertions.assertEquals(created, loaded)
    }

    @Test
    fun createDuplicateName() {
        Assertions.assertThrows(DuplicateCoordsException::class.java) {
            testSubject.create(SectorData(
                    galaxy = GALAXY_ID,
                    x = 0,
                    y = 0,
                    stars = 5
            ))
        }
    }

}
