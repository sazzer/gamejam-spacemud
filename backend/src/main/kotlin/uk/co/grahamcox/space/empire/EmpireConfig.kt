package uk.co.grahamcox.space.empire

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.empire.dao.JdbcEmpireDaoImpl
import java.time.Clock

/**
 * Spring configuration for working with Empires
 */
@Configuration
class EmpireConfig {
    @Bean
    fun empireDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate) =
            JdbcEmpireDaoImpl(clock, jdbcTemplate)
}
