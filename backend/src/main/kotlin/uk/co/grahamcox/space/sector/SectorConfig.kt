package uk.co.grahamcox.space.sector

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.sector.dao.JdbcSectorDaoImpl
import java.time.Clock

/**
 * Spring configuration for working with Galaxies
 */
@Configuration
class SectorConfig {
    @Bean
    fun sectorDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate) =
            JdbcSectorDaoImpl(clock, jdbcTemplate)
}
