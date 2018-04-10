package uk.co.grahamcox.space.spring

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.time.Clock
import java.time.ZoneId

/**
 * Core Spring configuration for the application
 */
@Configuration
@Import(
        DatabaseConfig::class,
        WebMvcConfig::class
)
class ApplicationConfig {
    /**
     * The clock to use
     */
    @Bean
    fun clock() = Clock.system(ZoneId.of("UTC"))

    /**
     * The Rest Template to use
     */
    @Bean
    fun restTemplate() = RestTemplateBuilder().build()
}
