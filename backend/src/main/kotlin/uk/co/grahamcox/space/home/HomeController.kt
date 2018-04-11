package uk.co.grahamcox.space.home

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import uk.co.grahamcox.space.hal.Link
import uk.co.grahamcox.space.hal.buildUri

/**
 * Controller to return the API Root
 */
@RestController
class HomeController {
    /**
     * Get the API Home document
     */
    @RequestMapping(value = ["/api"], method = [RequestMethod.GET], produces = ["application/hal+json"])
    fun getApiHome(): HomeModel {
        return HomeModel(
                links = HomeLinks(
                        self = Link(
                                href = HomeController::getApiHome.buildUri()
                        ),
                        websocket = Link(
                                href = buildWebsocketLink(),
                                type = null
                        )
                )
        )
    }

    /**
     * Build the URI for the Websocket connection
     */
    private fun buildWebsocketLink(): String {
        val currentRequest = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri()

        val scheme = if (currentRequest.scheme == "http") {
            "ws"
        } else {
            "wss"
        }

        val result = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/ws")
                .replaceQuery(null)
                .scheme(scheme)
                .build()
                .toUriString()

        return result
    }
}
