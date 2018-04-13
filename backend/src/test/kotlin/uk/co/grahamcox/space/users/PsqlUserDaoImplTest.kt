package uk.co.grahamcox.space.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.spring.SpringTestBase
import java.time.Instant

/**
 * Tests for the [PsqlUserDaoImpl]
 */
@Sql("classpath:uk/co/grahamcox/space/users/data.sql")
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

    /**
     * Test getting a known user by ID
     */
    @Test
    fun getKnownUserById() {
        val user = testSubject.getById(UserId("00000000-0000-0000-0000-000000000001"))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(UserId("00000000-0000-0000-0000-000000000001"), user.identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", user.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), user.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), user.identity.updated) },
                Executable { Assertions.assertEquals("test@user.example.com", user.data.email) },
                Executable { Assertions.assertEquals("Test User", user.data.displayName) },
                Executable { Assertions.assertEquals("\$2a\$10\$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy", user.data.password) }
        )
    }

    /**
     * Test getting an unknown user by email
     */
    @Test
    fun getUnknownUserByEmail() {
        val user = testSubject.getByEmail("unknown@example.com")

        Assertions.assertNull(user)
    }

    /**
     * Test getting a known user by email
     */
    @Test
    fun getKnownUserByEmail() {
        val user = testSubject.getByEmail("test@user.example.com")

        Assertions.assertNotNull(user)
        user!!

        Assertions.assertAll(
                Executable { Assertions.assertEquals(UserId("00000000-0000-0000-0000-000000000001"), user.identity.id) },
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", user.identity.version) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T08:29:00Z"), user.identity.created) },
                Executable { Assertions.assertEquals(Instant.parse("2018-03-28T09:29:00Z"), user.identity.updated) },
                Executable { Assertions.assertEquals("test@user.example.com", user.data.email) },
                Executable { Assertions.assertEquals("Test User", user.data.displayName) },
                Executable { Assertions.assertEquals("\$2a\$10\$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy", user.data.password) }
        )
    }

    /**
     * Test creating a new user successfully
     */
    @Test
    fun createUserSuccess() {
        val createdUser = testSubject.create(UserData(
                email = "new@example.com",
                displayName = "New User",
                password = "SomeEncodedPassword"
        ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals("new@example.com", createdUser.data.email) },
                Executable { Assertions.assertEquals("New User", createdUser.data.displayName) },
                Executable { Assertions.assertEquals("SomeEncodedPassword", createdUser.data.password) }
        )

        val retrievedUser = testSubject.getById(createdUser.identity.id)

        Assertions.assertEquals(createdUser, retrievedUser)
    }
}
