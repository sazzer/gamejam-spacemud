package uk.co.grahamcox.space

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.LoggerFactory
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.UriComponentsBuilder

/**
 * Annotation to specify the current request for a test
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@ExtendWith(CurrentRequestExtension::class)
annotation class CurrentRequest(val url: String = "http://localhost:8080")

/**
 * JUnit Extension to manage the Current Request used in a test
 */
class CurrentRequestExtension : BeforeEachCallback, AfterEachCallback {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CurrentRequestExtension::class.java)
    }

    /**
     * Callback that is invoked *before* each test is invoked.

     * @param context the current extension context; never `null`
     */
    override fun beforeEach(context: ExtensionContext) {
        val url = getCurrentUrl(context)

        url?.apply {
            LOG.debug("Setting current request to {}", url)
            val uriComponents = UriComponentsBuilder.fromUriString(url).build()
            val request = MockHttpServletRequest("GET", uriComponents.path)
            request.scheme = uriComponents.scheme
            request.serverName = uriComponents.host
            request.serverPort = uriComponents.port
            request.queryString = uriComponents.query

            val requestAttributes = ServletRequestAttributes(request)
            RequestContextHolder.setRequestAttributes(requestAttributes)
        }
    }

    /**
     * Callback that is invoked *after* each test has been invoked.

     * @param context the current extension context; never `null`
     */
    override fun afterEach(context: ExtensionContext) {
        LOG.debug("Resetting current request")
        RequestContextHolder.resetRequestAttributes()
    }

    /**
     * Get the current URL for the test
     * @param context the current extension context; never `null`
     * @return the current URL. Null if there is no [CurrentRequest] annotation on the class or method
     */
    private fun getCurrentUrl(context: ExtensionContext): String? {
        val methodUrl = context.testMethod
                .map { it.getAnnotation(CurrentRequest::class.java) }
                .orElseGet {
                    context.testClass
                            .map { it.getAnnotation(CurrentRequest::class.java) }
                            .orElse(null)
                }

        return methodUrl?.url
    }
}
