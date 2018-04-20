package uk.co.grahamcox.space.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.Executable

/**
 * Unit tests for [PasswordHasher]
 */
internal class PasswordHasherTest {
    /** The test subject */
    val testSubject = PasswordHasher(10)

    /**
     * The whole point of hashed passwords is that they are unpredictable and irreversable.
     * As such, the only way to test it is to hash a password then compare to the plaintext
     */
    @Test
    fun testHashAndCompare() {
        val hashed = testSubject.hashPassword("password")
        System.out.println("Hashed password: $hashed")
        Assertions.assertAll(
                Executable { Assertions.assertNotEquals("password", hashed) },
                Executable { Assertions.assertTrue(testSubject.comparePasswords("password", hashed))}
        )
    }

    /**
     * Test that hasing the same password 100 times produces 100 different hashes
     */
    @Test
    fun testHashUnique() {
        val hashes = mutableSetOf<String>()

        (1..100).forEach {
            val hashed = testSubject.hashPassword("password")
            Assertions.assertFalse(hashes.contains(hashed))

            hashes.add(hashed)
        }

    }
    /**
     * Test comparing the hashed password to a similar password and ensure that they return a negative match
     */
    @TestFactory
    fun testCompareSimilar(): List<DynamicTest> {
        val hashed = testSubject.hashPassword("password")

        return listOf(
                "Password",
                "PASSWORD",
                "password1",
                "1password",
                " password",
                "password ",
                "passwor",
                "passwordd",
                "ppassword",
                hashed,
                ""
        )
                .map { input ->
                    DynamicTest.dynamicTest("Plaintext: $input") {
                        Assertions.assertFalse(testSubject.comparePasswords(input, hashed))
                    }
                }

    }
}
