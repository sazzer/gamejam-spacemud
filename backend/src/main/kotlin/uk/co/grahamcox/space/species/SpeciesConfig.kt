package uk.co.grahamcox.space.species

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.species.dao.JdbcSpeciesDaoImpl
import java.time.Clock

/**
 * Spring configuration for working with Species
 */
@Configuration
class SpeciesConfig {
    @Bean
    fun speciesDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate, objectMapper: ObjectMapper) =
            JdbcSpeciesDaoImpl(clock, jdbcTemplate, objectMapper)
}
