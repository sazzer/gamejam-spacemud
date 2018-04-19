package uk.co.grahamcox.space.users.rest

import uk.co.grahamcox.space.model.Resource
import uk.co.grahamcox.space.rest.hal.Link
import uk.co.grahamcox.space.rest.hal.Meta
import uk.co.grahamcox.space.rest.hal.buildUri
import uk.co.grahamcox.space.users.UserData
import uk.co.grahamcox.space.users.UserId

/**
 * Translator to translate a User resource into a REST Representation
 */
class UserTranslator {
    /**
     * Translate the given User resource into the API representation
     * @param user The user to translate
     * @return the translated user
     */
    fun translate(user: Resource<UserId, UserData>) = UserModel(
            links = UserLinks(
                    self = Link(
                            href = GetUserController::getUserById.buildUri(user.identity.id.id)
                    )
            ),
            meta = Meta(
                    created = user.identity.created,
                    updated = user.identity.updated,
                    version = user.identity.version
            ),
            email = user.data.email,
            displayName = user.data.displayName
    )
}
