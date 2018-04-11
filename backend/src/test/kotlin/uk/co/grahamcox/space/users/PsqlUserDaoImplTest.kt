package uk.co.grahamcox.space.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Tests for the [PsqlUserDaoImpl]
 */
internal class PsqlUserDaoImplTest : SpringTestBase() {
    /** The test subject */
    @Autowired
    private lateinit var testSubject: UserDao

    /**
     * Test getting an unknown user by ID
     */
    @Test
    fun getUnknownUserById() {
        val userId = UserId("00000000-0000-0000-0000-000000000000")
        val e = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            testSubject.getById(userId)
        }

        Assertions.assertEquals(userId, e.id)
    }
}
