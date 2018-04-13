package uk.co.grahamcox.space.home

import com.fasterxml.jackson.annotation.JsonProperty
import uk.co.grahamcox.space.rest.hal.Link

/**
 * Links in the Home document
 * @property self The self link
 * @property websocket The link for the websocket connection
 */
data class HomeLinks(
        @JsonProperty("self") val self: Link,
        @JsonProperty("space/ws:join") val websocket: Link
)

/**
 * Representation of the Home document
 * @property links The links
 */
data class HomeModel(
        @JsonProperty("_links") val links: HomeLinks
)
