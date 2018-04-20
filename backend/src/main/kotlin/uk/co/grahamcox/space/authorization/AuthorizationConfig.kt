package uk.co.grahamcox.space.authorization

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
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
    fun accessTokenGenerator(clock: Clock, @Value("\${space.authorization.accessToken.duration}") duration: String) =
            AccessTokenGenerator(clock, Duration.parse(duration), UUIDAccessTokenIdGeneratorImpl())

    @Bean
    fun accessTokenSerializer(@Value("\${space.authorization.accessToken.secret}") secret: String) =
            JwtAccessTokenSerializerImpl(Algorithm.HMAC512(secret))

    @Bean
    fun accessTokenTranslator(clock: Clock, accessTokenSerializer: AccessTokenSerializer) = AccessTokenTranslator(clock, accessTokenSerializer)
}
