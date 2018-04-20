package uk.co.grahamcox.space.users.rest

import uk.co.grahamcox.space.authorization.AccessTokenGenerator
import uk.co.grahamcox.space.authorization.rest.AccessTokenSerializer
import uk.co.grahamcox.space.authorization.rest.AccessTokenTranslator
import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.rest.hal.Link
import uk.co.grahamcox.space.rest.hal.Meta
import uk.co.grahamcox.space.rest.hal.buildUri
import uk.co.grahamcox.space.users.UserData
import uk.co.grahamcox.space.users.UserId

/**
 * Translator to translate a User resource into a REST Representation
 */
class AuthorizedUserTranslator(
        private val userTranslator: UserTranslator,
        private val accessTokenGenerator: AccessTokenGenerator,
        private val accessTokenTranslator: AccessTokenTranslator
) {
    /**
     * Translate the given User resource into the API representation
     * @param user The user to translate
     * @return the translated user
     */
    fun translate(user: Resource<UserId, UserData>): AuthorizedUserModel {
        val accessToken = accessTokenGenerator.generate(user.identity.id)
        return AuthorizedUserModel(
                user = userTranslator.translate(user),
                embedded = AuthorizedUserEmbedded(
                        token = accessTokenTranslator.translate(accessToken)
                )
        )
    }
}
