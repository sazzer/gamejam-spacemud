package uk.co.grahamcox.space.spring

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import uk.co.grahamcox.space.authorization.AuthorizationConfig
import uk.co.grahamcox.space.galaxy.GalaxyConfig
import uk.co.grahamcox.space.generation.empire.EmpireGeneratorConfig
import uk.co.grahamcox.space.generation.galaxy.GalaxyGeneratorConfig
import uk.co.grahamcox.space.generation.species.SpeciesGeneratorConfig
import uk.co.grahamcox.space.generation.starmap.StarMapGeneratorConfig
import uk.co.grahamcox.space.generation.markov.MarkovChainConfig
import uk.co.grahamcox.space.home.HomeController
import uk.co.grahamcox.space.sector.SectorConfig
import uk.co.grahamcox.space.species.SpeciesConfig
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
        GalaxyGeneratorConfig::class,
        GalaxyConfig::class,
        SpeciesConfig::class,
        SectorConfig::class
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
