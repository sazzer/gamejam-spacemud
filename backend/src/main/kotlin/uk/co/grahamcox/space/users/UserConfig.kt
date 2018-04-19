package uk.co.grahamcox.space.users

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.users.dao.PsqlUserDaoImpl
import uk.co.grahamcox.space.users.dao.UserDao
import uk.co.grahamcox.space.users.rest.CreateUserController
import uk.co.grahamcox.space.users.rest.UserTranslator
import uk.co.grahamcox.space.users.rest.GetUserController
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

    /**
     * The means to translate user resources
     */
    @Bean
    fun userTranslator() = UserTranslator()

    /**
     * The means to hash passwords
     */
    @Bean
    fun passwordHasher() = PasswordHasher()

    /**
     * The Get Users Controller
     */
    @Bean
    fun getUserController(userDao: UserDao, userTranslator: UserTranslator) = GetUserController(userDao, userTranslator)

    /**
     * The Create Users Controller
     */
    @Bean
    fun createUserController(userDao: UserDao, passwordHasher: PasswordHasher, userTranslator: UserTranslator) =
            CreateUserController(userDao, passwordHasher, userTranslator)
}
