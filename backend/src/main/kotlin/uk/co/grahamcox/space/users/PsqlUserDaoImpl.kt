package uk.co.grahamcox.space.users

import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.dao.ResourceNotFoundException
import uk.co.grahamcox.space.model.Identity
import uk.co.grahamcox.space.model.Resource
import java.sql.ResultSet
import java.time.Clock

/**
 * Postgres JDBC Implementation of the User DAO
 */
class PsqlUserDaoImpl(val clock: Clock, val jdbcTemplate: NamedParameterJdbcTemplate) : UserDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(PsqlUserDaoImpl::class.java)
    }
    /**
     * Get the User by their unique ID
     * @param id The ID of the user
     * @return the user
     */
    override fun getById(id: UserId): Resource<UserId, UserData> {
        LOG.debug("Loading user with ID: {}", id)
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = :userId::uuid",
                    mapOf(
                            "userId" to id.id
                    )) { rs, _ -> parseUser(rs) }!!
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No user found with ID {}", id)
            throw ResourceNotFoundException(id)
        }
    }

    /**
     * Get the user by their email address
     * @param email The email address of the user
     * @return the email address
     */
    override fun getByEmail(email: String): Resource<UserId, UserData>? {
        LOG.debug("Loading user with Email: {}", email)
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = :email",
                    mapOf(
                            "email" to email
                    )) { rs, _ -> parseUser(rs) }
        } catch (e: EmptyResultDataAccessException) {
            LOG.warn("No user found with Email {}", email)
            null
        }
    }

    /**
     * Create a new user
     * @param user The user details
     * @return the user
     */
    override fun create(user: UserData): Resource<UserId, UserData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Parse the user details from the current row of the given resultset
     */
    private fun parseUser(rs: ResultSet) : Resource<UserId, UserData> {
        val identity = Identity(
                id = UserId(rs.getString("user_id")),
                version = rs.getString("version"),
                created = rs.getTimestamp("created").toInstant(),
                updated = rs.getTimestamp("updated").toInstant()
        )

        val data = UserData(
                email = rs.getString("email"),
                displayName = rs.getString("display_name"),
                password = rs.getString("password")
        )

        return Resource(identity, data)
    }
}
