package uk.co.grahamcox.space.rest.hal

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

/**
 * Helper to build a URI to an MVC Controller method
 */
fun <R> KFunction<R>.buildUri(vararg args: Any?): String {
    val javaFunction = this.javaMethod
    val javaClass = javaFunction?.declaringClass

    return MvcUriComponentsBuilder.fromMethod(javaClass, javaMethod, *args)
            .build()
            .toUriString()
}
