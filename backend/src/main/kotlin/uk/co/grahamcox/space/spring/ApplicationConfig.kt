package uk.co.grahamcox.space.spring

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import uk.co.grahamcox.space.authorization.AuthorizationConfig
import uk.co.grahamcox.space.galaxy.empire.generation.EmpireGeneratorConfig
import uk.co.grahamcox.space.galaxy.generation.GalaxyGeneratorConfig
import uk.co.grahamcox.space.galaxy.species.generation.SpeciesGeneratorConfig
import uk.co.grahamcox.space.galaxy.starmap.generation.StarMapGeneratorConfig
import uk.co.grahamcox.space.generation.markov.MarkovChainConfig
import uk.co.grahamcox.space.home.HomeController
import uk.co.grahamcox.space.users.UserConfig
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
        AuthorizationConfig::class,
        UserConfig::class,
        MarkovChainConfig::class,
        StarMapGeneratorConfig::class,
        SpeciesGeneratorConfig::class,
        EmpireGeneratorConfig::class,
        GalaxyGeneratorConfig::class
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
