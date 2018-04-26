package uk.co.grahamcox.space.integration.requester

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Option
import org.springframework.http.ResponseEntity

/**
 * Representation of the Response from an HTTP Request
 * @property responseEntity The actual response
 */
data class Response<T>(val responseEntity: ResponseEntity<T>) {
    /** The status code of the response */
    val statusCode = responseEntity.statusCode

    /** The HTTP Response body */
    val body = responseEntity.body

    /** The HTTP Headers */
    val headers = responseEntity.headers

    /** The JsonPath Parsing Context */
    val bodyContext = JsonPath
            .using(Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL))
            .parse(body ?: emptyMap<String, Any?>())
    /**
     * The value from the provided JSON Path in the body
     * @param path The JSON Path
     * @return the value
     */
    fun bodyValue(path: String): Any? = bodyContext.read(path)
}
