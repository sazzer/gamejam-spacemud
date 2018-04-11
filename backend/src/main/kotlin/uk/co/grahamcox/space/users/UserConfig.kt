package uk.co.grahamcox.space.users

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.Clock

/**
 * Spring configuration for the User module
 */
@Configuration
class UserConfig {
    /**
     * The User DAO
     */
    @Bean
    fun userDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate) = PsqlUserDaoImpl(clock, jdbcTemplate)
}