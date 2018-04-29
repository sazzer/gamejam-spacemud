package uk.co.grahamcox.space.generation.starmap

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
