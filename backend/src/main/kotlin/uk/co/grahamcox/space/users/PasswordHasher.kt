package uk.co.grahamcox.space.users

import org.mindrot.jbcrypt.BCrypt

/**
 * Helper to hash and compare passwords
 * @property saltRounds The number of log rounds for salt empire
 */
class PasswordHasher(private val saltRounds: Int) {
    /**
     * Hash the given password, producing an irreversable string
     * @param password The password to hash
     * @return the hashed password
     */
    fun hashPassword(password: String) = BCrypt.hashpw(password, BCrypt.gensalt(saltRounds))

    /**
     * Compare a plaintext and hashed password to see if they match
     * @param plaintext The plaintext password
     * @param hashed The hashed password
     * @return True if the two correspond. False if not
     */
    fun comparePasswords(plaintext: String, hashed: String) = BCrypt.checkpw(plaintext, hashed)
}
