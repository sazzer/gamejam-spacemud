package uk.co.grahamcox.space.galaxy

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.galaxy.dao.JdbcGalaxyDaoImpl
import java.time.Clock

/**
 * Spring configuration for working with Galaxies
 */
@Configuration
class GalaxyConfig {
    @Bean
    fun galaxyDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate) =
            JdbcGalaxyDaoImpl(clock, jdbcTemplate)
}
