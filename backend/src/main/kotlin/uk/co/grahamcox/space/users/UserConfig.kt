package uk.co.grahamcox.space.users

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.authorization.AccessTokenGenerator
import uk.co.grahamcox.space.authorization.rest.AccessTokenTranslator
import uk.co.grahamcox.space.users.dao.PsqlUserDaoImpl
import uk.co.grahamcox.space.users.dao.UserDao
import uk.co.grahamcox.space.users.rest.*
import java.time.Clock

/**
 * Spring configuration for the User module
 */
@Configuration
class UserConfig {
    @Bean
    fun userDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate) = PsqlUserDaoImpl(clock, jdbcTemplate)

    @Bean
    fun userTranslator() = UserTranslator()

    @Bean
    fun authorizedUserTranslator(userTranslator: UserTranslator,
                                 accessTokenGenerator: AccessTokenGenerator,
                                 accessTokenTranslator: AccessTokenTranslator) =
            AuthorizedUserTranslator(userTranslator, accessTokenGenerator, accessTokenTranslator)

    @Bean
    fun passwordHasher() = PasswordHasher()

    @Bean
    fun getUserController(userDao: UserDao, userTranslator: UserTranslator) = GetUserController(userDao, userTranslator)

    @Bean
    fun createUserController(userDao: UserDao, passwordHasher: PasswordHasher, userTranslator: AuthorizedUserTranslator) =
            CreateUserController(userDao, passwordHasher, userTranslator)

    @Bean
    fun authenticationController(userDao: UserDao, passwordHasher: PasswordHasher, userTranslator: AuthorizedUserTranslator) =
            AuthenticationController(userDao, passwordHasher, userTranslator)
}
