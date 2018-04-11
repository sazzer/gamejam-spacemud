package uk.co.grahamcox.space

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Controller to return the API Root
 */
@RestController
class HomeController {
    /**
     * Get the API Home document
     */
    @RequestMapping(value = ["/api"], method = [RequestMethod.GET], produces = ["application/hal+json"])
    fun getApiHome(): Map<String, Any?> {
        return emptyMap()
    }
}
