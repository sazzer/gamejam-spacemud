package uk.co.grahamcox.space.galaxy.dao

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.galaxy.GalaxyData
import uk.co.grahamcox.space.galaxy.GalaxyId
import uk.co.grahamcox.space.spring.SpringTestBase
import java.time.Instant

/**
 * Tests for the [JdbcGalaxyDaoImpl]
 */
@Sql("classpath:uk/co/grahamcox/space/galaxy/dao/data.sql")
internal class JdbcGalaxyDaoImplTest : SpringTestBase() {
    /** ID that doesn't exist */
    private val NON_EXISTANT_ID = GalaxyId("00000000-0000-0000-0000-000000000000")
    /** ID that does exist */
    private val EXISTING_ID = GalaxyId("00000000-0000-0000-0000-000000000001")

    @Autowired
    private lateinit var testSubject: GalaxyDao

    @Test
    fun getUnknownById() {
        val e = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubject.getById(NON_EXISTANT_ID)
        }

        Assertions.assertEquals(NON_EXISTANT_ID, e.id)
    }

    @Test
    fun getKnownById() {
        val galaxy = testSubject.getById(EXISTING_ID)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(EXISTING_ID, galaxy.identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", galaxy.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), galaxy.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), galaxy.identity.updated) },
                Executable { Assertions.assertEquals("Example Galaxy", galaxy.data.name) },
                Executable { Assertions.assertEquals(100, galaxy.data.width) },
                Executable { Assertions.assertEquals(200, galaxy.data.height) }
        )
    }

    @Test
    fun listGalaxies() {
        val page = testSubject.list()

        Assertions.assertAll(
                Executable { Assertions.assertEquals(2, page.totalCount) },

                Executable { Assertions.assertEquals(2, page.data.size) },

                Executable { Assertions.assertEquals(EXISTING_ID, page.data[0].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[0].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[0].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[0].identity.updated) },
                Executable { Assertions.assertEquals("Example Galaxy", page.data[0].data.name) },
                Executable { Assertions.assertEquals(100, page.data[0].data.width) },
                Executable { Assertions.assertEquals(200, page.data[0].data.height) },

                Executable { Assertions.assertEquals(GalaxyId("00000000-0000-0000-0000-000000000002"), page.data[1].identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", page.data[1].identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), page.data[1].identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), page.data[1].identity.updated) },
                Executable { Assertions.assertEquals("Second Galaxy", page.data[1].data.name) },
                Executable { Assertions.assertEquals(100, page.data[1].data.width) },
                Executable { Assertions.assertEquals(200, page.data[1].data.height) }
        )
    }

    @Test
    fun createGalaxy() {
        val created = testSubject.create(GalaxyData(
                name = "New Galaxy",
                width = 500,
                height = 750
        ))

        Assertions.assertAll(
                Executable { Assertions.assertNotNull(created.identity.id) },
                Executable { Assertions.assertNotNull(created.identity.version) },
                Executable { Assertions.assertNotNull(created.identity.created) },
                Executable { Assertions.assertNotNull(created.identity.updated) },
                Executable { Assertions.assertEquals("New Galaxy", created.data.name) },
                Executable { Assertions.assertEquals(500, created.data.width) },
                Executable { Assertions.assertEquals(750, created.data.height) }
        )

        val loaded = testSubject.getById(created.identity.id)

        Assertions.assertEquals(created, loaded)
    }

    @Test
    fun createDuplicateName() {
        Assertions.assertThrows(DuplicateNameException::class.java) {
            testSubject.create(GalaxyData(
                    name = "Second Galaxy",
                    width = 500,
                    height = 750
            ))
        }
    }

}
