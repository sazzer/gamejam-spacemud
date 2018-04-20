package uk.co.grahamcox.space.authorization

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Unit tests for [UUIDAccessTokenIdGeneratorImpl]
 */
internal class UUIDAccessTokenIdGeneratorImplTest {
    /** the test subject */
    val testSystem = UUIDAccessTokenIdGeneratorImpl()

    /**
     * We can't test that the correct UUID was generated, but we can test that it's the right shape
     */
    @Test
    fun testShape() {
        val accessTokenId = testSystem.generate()

        Assertions.assertTrue("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$".toRegex().matches(accessTokenId.id))
    }
}
