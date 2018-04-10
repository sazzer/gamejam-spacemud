package uk.co.grahamcox.space.spring

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.space.integration.requester.Requester
import uk.co.grahamcox.space.spring.database.DatabaseCleaner
import javax.sql.DataSource

/**
 * Spring Config for the Integration tests
 */
@Configuration
class IntegrationTestConfig {
    /**
     * The means to make HTTP Requests
     */
    @Bean
    fun requester(restTemplate: TestRestTemplate) = Requester(restTemplate)

    /**
     * Mechanism by which the database can be cleaned
     */
    @Bean
    fun databaseCleaner(dataSource: DataSource) =
            DatabaseCleaner(dataSource, listOf("flyway_schema_history"))
}
