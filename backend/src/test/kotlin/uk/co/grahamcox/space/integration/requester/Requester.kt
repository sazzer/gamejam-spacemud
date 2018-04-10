package uk.co.grahamcox.space.integration.requester

import org.slf4j.LoggerFactory
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

/**
 * Mechanism to make requests to the test server
 * @property restTemplate The means to make requests
 */
class Requester(
        private val restTemplate: TestRestTemplate
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(Requester::class.java)
    }

    /** The access token to use, if any */
    var accessToken: String? = null

    /**
     * Make a GET request to the given URI
     * @param uri The URI
     * @param headers The headers provided by the caller
     * @return the last response
     */
    fun get(uri: String, headers: Map<String, String> = mapOf(), responseType: Class<*> = Any::class.java) =
            makeRequest(uri, HttpMethod.GET, null, headers, responseType)

    /**
     * Make a POST request to the given URI
     * @param uri The URI to make a POST to
     * @param body The body to post
     * @param headers The headers provided by the caller
     * @return the last response
     */
    fun post(uri: String, body: Any, headers: Map<String, String> = mapOf(), responseType: Class<*> = Any::class.java) =
            makeRequest(uri, HttpMethod.POST, body, headers, responseType)
    /**
     * Make a PUT request to the given URI
     * @param uri The URI to make a PUT to
     * @param body The body to put
     * @param headers The headers provided by the caller
     * @return the last response
     */
    fun put(uri: String, body: Any, headers: Map<String, String> = mapOf(), responseType: Class<*> = Any::class.java) =
            makeRequest(uri, HttpMethod.PUT, body, headers, responseType)

    /**
     * Make a DELETE request to the given URI
     * @param uri The URI
     * @param body The body to provide, if any
     * @param headers The headers provided by the caller
     * @return the last response
     */
    fun delete(uri: String, body: Any? = null, headers: Map<String, String> = mapOf(), responseType: Class<*> = Any::class.java) =
            makeRequest(uri, HttpMethod.DELETE, body, headers, responseType)

    /**
     * Actually make a request and retrive the response
     * @param uri The URI to make the request to
     * @param method The HTTP Method to use for the request
     * @param body The body to send, if any
     * @param headers The headers provided by the caller
     * @return the last response
     */
    fun <T> makeRequest(uri: String,
                    method: HttpMethod,
                    body: Any?,
                    headers: Map<String, String>,
                    responseType: Class<T>): Response<T> {
        val lastResponseEntity = restTemplate.exchange(
                uri,
                method,
                HttpEntity(body, buildHeaders(headers)),
                responseType) as ResponseEntity<T>

        LOG.debug("Request to {} {} with payload {} and headers {} returned response {}",
                method, uri, body, headers, lastResponseEntity)
        return Response(lastResponseEntity)
    }

    /**
     * Helper to build the headers needed for this request
     * @param providedHeaders The headers provided by the caller
     * @return the HTTP Headers
     */
    private fun buildHeaders(providedHeaders: Map<String, String>): HttpHeaders {
        val headers = HttpHeaders()
        providedHeaders.forEach { (header, value) -> headers.set(header, value) }
        accessToken?.let {
            headers.set("Authorization", "Bearer $accessToken")
        }
        return headers
    }
}
