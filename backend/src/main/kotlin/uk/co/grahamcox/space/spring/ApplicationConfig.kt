package uk.co.grahamcox.space.spring

import org.apache.catalina.startup.UserConfig
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import uk.co.grahamcox.space.home.HomeController
import uk.co.grahamcox.space.websocket.WebSocketConfig
import java.time.Clock
import java.time.ZoneId

/**
 * Core Spring configuration for the application
 */
@Configuration
@Import(
        DatabaseConfig::class,
        WebMvcConfig::class,
        WebSocketConfig::class,
        UserConfig::class
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

    /**
     * The API Home Controller
     */
    @Bean
    fun homeController() = HomeController()
}
