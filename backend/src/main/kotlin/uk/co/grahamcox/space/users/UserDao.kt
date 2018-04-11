package uk.co.grahamcox.space.users

import uk.co.grahamcox.space.model.Resource

/**
 * DAO for working with User records
 */
interface UserDao {
    /**
     * Get the User by their unique ID
     * @param id The ID of the user
     * @return the user
     */
    fun getById(id: UserId) : Resource<UserId, UserData>

    /**
     * Get the user by their email address
     * @param email The email address of the user
     * @return the email address
     */
    fun getByEmail(email: String) : Resource<UserId, UserData>?

    /**
     * Create a new user
     * @param user The user details
     * @return the user
     */
    fun create(user: UserData) : Resource<UserId, UserData>
}
