package uk.co.grahamcox.space.authorization

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.space.authorization.rest.AccessTokenSerializer
import uk.co.grahamcox.space.authorization.rest.AccessTokenTranslator
import uk.co.grahamcox.space.authorization.rest.JwtAccessTokenSerializerImpl
import java.time.Clock
import java.time.Duration

/**
 * Spring configuration for Authorization
 */
@Configuration
class AuthorizationConfig {
    @Bean
    fun accessTokenGenerator(clock: Clock) = AccessTokenGenerator(clock, Duration.ofDays(10), UUIDAccessTokenIdGeneratorImpl())

    @Bean
    fun accessTokenSerializer() = JwtAccessTokenSerializerImpl(Algorithm.HMAC512("SomeSuperSecret"))

    @Bean
    fun accessTokenTranslator(clock: Clock, accessTokenSerializer: AccessTokenSerializer) = AccessTokenTranslator(clock, accessTokenSerializer)
}
