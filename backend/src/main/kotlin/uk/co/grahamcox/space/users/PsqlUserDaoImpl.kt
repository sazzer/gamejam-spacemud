package uk.co.grahamcox.space.users

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.model.Resource
import java.time.Clock

/**
 * Postgres JDBC Implementation of the User DAO
 */
class PsqlUserDaoImpl(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate) : UserDao {
    /**
     * Get the User by their unique ID
     * @param id The ID of the user
     * @return the user
     */
    override fun getById(id: UserId): Resource<UserId, UserData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the user by their email address
     * @param email The email address of the user
     * @return the email address
     */
    override fun getByEmail(email: String): Resource<UserId, UserData>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Create a new user
     * @param user The user details
     * @return the user
     */
    override fun create(user: UserData): Resource<UserId, UserData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
