package uk.co.grahamcox.space.authorization.rest

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.space.authorization.AccessToken
import uk.co.grahamcox.space.authorization.AccessTokenId
import uk.co.grahamcox.space.users.UserId
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

/**
 * Unit tests for [AccessTokenTranslator]
 */
internal class AccessTokenTranslatorTest {
    /** The "Current" time */
    private val NOW = Instant.parse("2018-04-19T20:49:00Z")

    /** The mock Access Token Serializer */
    private val accessTokenSerializer: AccessTokenSerializer = mockk()

    /** The test subject */
    private val testSubject = AccessTokenTranslator(
            clock = Clock.fixed(NOW, ZoneId.of("UTC")),
            accessTokenSerializer = accessTokenSerializer
    )

    /**
     * Test the translation into an access token
     */
    @Test
    fun testTranslate() {
        val accessToken = AccessToken(
                id = AccessTokenId("acToId"),
                userId = UserId("user"),
                created = NOW,
                expires = NOW.plusSeconds(3600)
        )

        every { accessTokenSerializer.serialize(accessToken) } returns "serialized"

        val result = testSubject.translate(accessToken)

        Assertions.assertAll(
                Executable { Assertions.assertEquals("serialized", result.accessToken) },
                Executable { Assertions.assertEquals(3600, result.expires) },
                Executable { Assertions.assertEquals("Bearer", result.tokenType) }
        )
    }
}
