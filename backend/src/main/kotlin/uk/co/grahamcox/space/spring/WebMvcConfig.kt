package uk.co.grahamcox.space.spring

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Support for adding any necessary Web Mvc Configuration
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {
    /**
     * Allows cross-origin calls to any API handler
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
    }
}
