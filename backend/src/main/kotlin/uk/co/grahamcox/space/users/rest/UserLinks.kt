package uk.co.grahamcox.space.users.rest

import uk.co.grahamcox.space.rest.hal.Link

/**
 * Links relevant to a User record
 * @property self The self link for the user
 */
data class UserLinks(
        val self: Link
)
