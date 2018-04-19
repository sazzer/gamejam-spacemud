package uk.co.grahamcox.space.authorization.rest

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.SignatureVerificationException
import org.slf4j.LoggerFactory
import uk.co.grahamcox.space.authorization.AccessToken
import uk.co.grahamcox.space.authorization.AccessTokenId
import uk.co.grahamcox.space.users.UserId
import java.util.*

/**
 * Implementation of the Access Token Serializer that converts it into a JWT
 */
class JwtAccessTokenSerializerImpl(private val signingAlgorithm: Algorithm) : AccessTokenSerializer {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JwtAccessTokenSerializerImpl::class.java)
    }
    /**
     * Serialize the Access Token into a string
     * @param the access token
     * @return the string
     */
    override fun serialize(accessToken: AccessToken): String {
        val jwt = JWT.create()
                .withIssuer(JwtAccessTokenSerializerImpl::class.qualifiedName)
                .withAudience(JwtAccessTokenSerializerImpl::class.qualifiedName)
                .withJWTId(accessToken.id.id)
                .withSubject(accessToken.userId.id)
                .withNotBefore(Date.from(accessToken.created))
                .withIssuedAt(Date.from(accessToken.created))
                .withExpiresAt(Date.from(accessToken.expires))
                .sign(signingAlgorithm)
        LOG.debug("Generated JWT {} for access token {}", jwt, accessToken)
        return jwt
    }

    /**
     * Deserialize the Access Token from a string
     * @param accessToken The string
     * @return the access token
     */
    override fun deserialize(accessToken: String): AccessToken {
        val decodedJWT = try {
            JWT.require(signingAlgorithm)
                    .withAudience(JwtAccessTokenSerializerImpl::class.qualifiedName)
                    .withIssuer(JwtAccessTokenSerializerImpl::class.qualifiedName)
                    .build()
                    .verify(accessToken)
        } catch (e: JWTDecodeException) {
            LOG.warn("Error decoding access token {}", accessToken, e)
            throw InvalidAccessTokenException(accessToken, "Error decoding access token", e)
        } catch (e: SignatureVerificationException) {
            LOG.warn("Invalid access token signature {}", accessToken, e)
            throw InvalidAccessTokenException(accessToken, "Invalid access token signature", e)
        }

        val user = UserId(decodedJWT.subject)
        val tokenId = AccessTokenId(decodedJWT.id)
        val created = decodedJWT.issuedAt.toInstant()
        val expires = decodedJWT.expiresAt.toInstant()

        val result = AccessToken(
                id = tokenId,
                userId = user,
                created = created,
                expires = expires)
        LOG.debug("Deserialized JWT {} into access token {}", accessToken, result)
        return result
    }
}
