package uk.co.grahamcox.space.galaxy.starmap.generation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring config for Star Map Generation
 */
@Configuration
class StarMapGeneratorConfig {
    @Bean
    fun starMapGenerator() = ChiSquaredRadialStarMapGeneratorImpl()
}
