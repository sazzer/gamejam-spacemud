package uk.co.grahamcox.space.integration

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import uk.co.grahamcox.space.spring.PostgresWrapper
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Test the healthchecks
 */
class HealthchecksIT : SpringTestBase() {
    /** The postgres database */
    @Autowired
    private lateinit var postgresWrapper: PostgresWrapper

    /**
     * Test that checks things all work correctly
     */
    @Test
    fun testAllOk() {
        val response = requester.get("/actuator/health")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { Assertions.assertEquals("UP", response.bodyValue("status")) },
                Executable { Assertions.assertEquals("UP", response.bodyValue("details.diskSpace.status")) },
                Executable { Assertions.assertEquals("UP", response.bodyValue("details.db.status")) }
        )
    }

    /**
     * Test that checks the healthcheck reports errors if the database isn't available
     */
    @Test
    @DirtiesContext
    fun testDownIfDbDown() {
        postgresWrapper.stop()
        val response = requester.get("/actuator/health")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.statusCode) },

                Executable { Assertions.assertEquals("DOWN", response.bodyValue("status")) },
                Executable { Assertions.assertEquals("UP", response.bodyValue("details.diskSpace.status")) },
                Executable { Assertions.assertEquals("DOWN", response.bodyValue("details.db.status")) }
        )
    }
}
