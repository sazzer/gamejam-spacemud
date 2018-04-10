package uk.co.grahamcox.space.spring

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres
import ru.yandex.qatools.embed.postgresql.distribution.Version

/**
 * Wrapper around the Postgres Server
 */
class PostgresWrapper {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(PostgresWrapper::class.java)
    }

    /** The postgres server  */
    val postgres = EmbeddedPostgres(Version.V10_3)

    /** The database connection URL */
    lateinit var url: String

    /**
     * Start the server
     */
    fun start() {
        url = postgres.start()
        LOG.debug("Started Postgres server on {}", url)
    }

    /**
     * Stop the server
     */
    fun stop() {
        postgres.stop()
        LOG.debug("Stopping Postgres server on {}", url)
    }
}

/**
 * Spring configuration for the database
 */
@Configuration
@ConditionalOnProperty(value = "space.database.embedded", havingValue = "true")
class DatabaseConfig {
    /**
     * The Embedded Postgres server
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    fun embeddedPostgres() = PostgresWrapper()

    /**
     * The data source to use
     */
    @Bean
    fun datasource(postgres: PostgresWrapper) = DataSourceBuilder.create()
            .url(postgres.url)
            .build()
}
