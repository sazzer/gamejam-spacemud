package uk.co.grahamcox.space.authorization

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.space.users.UserId
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

/**
 * Unit tests for [AccessTokenGenerator]
 */
internal class AccessTokenGeneratorTest {
    /** the "current" time */
    val NOW = Instant.parse("2018-04-19T18:45:00Z")

    /** The mock Access Token ID Generator */
    val accessTokenIdGenerator: AccessTokenIdGenerator = mockk()

    /** The test subject */
    val testSubject = AccessTokenGenerator(Clock.fixed(NOW, ZoneId.of("UTC")),
            Duration.parse("P1D"),
            accessTokenIdGenerator)

    /**
     * Test generating an access token
     */
    @Test
    fun testGenerate() {
        val accessTokenId = AccessTokenId("acToId")
        every { accessTokenIdGenerator.generate() }.returns(accessTokenId)
        val user = UserId("someUser")

        val accessToken = testSubject.generate(user)

        Assertions.assertAll(
                Executable { Assertions.assertEquals(accessTokenId, accessToken.id) },
                Executable { Assertions.assertEquals(user, accessToken.userId) },
                Executable { Assertions.assertEquals(NOW, accessToken.created) },
                Executable { Assertions.assertEquals(NOW.plus(Duration.ofDays(1)), accessToken.expires) }
        )
    }
}
