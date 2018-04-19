package uk.co.grahamcox.space.spring

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.util.UriComponentsBuilder
import uk.co.grahamcox.space.integration.requester.Requester
import uk.co.grahamcox.space.spring.database.DatabaseCleanerTestExecutionListener

/**
 * Base class for all Integration tests, allowing set up of the Spring context
 */
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@ContextConfiguration(classes = [(IntegrationTestConfig::class)])
@SpringBootTest(classes = [(Application::class)], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
        listeners = [(DatabaseCleanerTestExecutionListener::class)],
                mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
class SpringTestBase {
    /** Means to make HTTP Requests */
    @Autowired
    lateinit var requester: Requester

    @LocalServerPort
    private lateinit var serverPort: Integer

    /** The object mapper to use */
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    /** The means to access the database */
    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    /**
     * Reset the access token in the requester
     */
    @BeforeEach
    fun resetRequester() {
        requester.accessToken = null
    }

    /**
     * Provide a URI Component Builder for building URIs on the server
     */
    fun uriBuilder() = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(serverPort.toString())

    /**
     * Helper to compare the expected JSON String to the actual value, which is interpreted JSON
     */
    fun jsonMatch(expected: String, actual: Any) {
        val actualJson = objectMapper.writeValueAsString(actual)
        val expectedJson = objectMapper.writeValueAsString(objectMapper.readValue(expected, Any::class.java))

        Assertions.assertEquals(expectedJson, actualJson)
    }
}
