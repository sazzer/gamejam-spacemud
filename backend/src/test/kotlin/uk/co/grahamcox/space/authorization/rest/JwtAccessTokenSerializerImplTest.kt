package uk.co.grahamcox.space.authorization.rest

import com.auth0.jwt.algorithms.Algorithm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.grahamcox.space.authorization.AccessToken
import uk.co.grahamcox.space.authorization.AccessTokenId
import uk.co.grahamcox.space.users.UserId
import java.time.Instant

/**
 * Unit Tests for the Access Token Serializer
 */
internal class JwtAccessTokenSerializerImplTest {
    /** The Access Token to serialize */
    private val accessToken = AccessToken(
            id = AccessTokenId("atid"),
            userId = UserId("someUser"),
            created = Instant.parse("2000-01-01T09:45:00Z"),
            expires = Instant.parse("3000-01-01T09:45:00Z")
    )

    /** The Serialized Access Token */
    private val serialized = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJhdWQiOiJ1ay5jby5ncmFoYW1jb3guc3BhY2UuYXV0aG9yaXphdGlvbi5yZXN0Lkp3dEFjY2Vzc1Rva2VuU2VyaWFsaXplckltcGwiLCJzdWIiOiJzb21lVXNlciIsIm5iZiI6OTQ2NzE5OTAwLCJpc3MiOiJ1ay5jby5ncmFoYW1jb3guc3BhY2UuYXV0aG9yaXphdGlvbi5yZXN0Lkp3dEFjY2Vzc1Rva2VuU2VyaWFsaXplckltcGwiLCJleHAiOjMyNTAzNzE1MTAwLCJpYXQiOjk0NjcxOTkwMCwianRpIjoiYXRpZCJ9" +
            ".WDd1tMNkqod8d0W1-GYd0jinxU8vAuEm7o8xSE0Dm50"

    /** The test subject */
    private val testSubject = JwtAccessTokenSerializerImpl(Algorithm.HMAC256("someSecret"))

    /**
     * Test serializing an access token
     */
    @Test
    fun testSerializeAccessToken() {
        val result = testSubject.serialize(accessToken)

        Assertions.assertEquals(serialized, result)
    }

    /**
     * Test deserializing an access token
     */
    @Test
    fun testDeserializeAccessToken() {
        val result = testSubject.deserialize(serialized)

        Assertions.assertEquals(result, accessToken)
    }

    /**
     * Test deserializing an invalid access token
     */
    @Test
    fun testDeserializeInvalidAccessToken() {
        val exception = Assertions.assertThrows(InvalidAccessTokenException::class.java) {
            testSubject.deserialize("Invalid")
        }

        Assertions.assertEquals("Invalid", exception.accessToken)
    }

    /**
     * Test deserializing an invalid access token
     */
    @Test
    fun testDeserializeInvalidSignedAccessToken() {
        val exception = Assertions.assertThrows(InvalidAccessTokenException::class.java) {
            testSubject.deserialize(serialized + "1")
        }

        Assertions.assertEquals(serialized + "1", exception.accessToken)
    }
}
