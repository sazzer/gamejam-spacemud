package uk.co.grahamcox.space.generation.species

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.space.generation.markov.MarkovChainBuilder

/**
 * Spring config for Species Generation
 */
@Configuration
class SpeciesGeneratorConfig {
    @Bean
    fun speciesGenerator(markovChainBuilder: MarkovChainBuilder) = SpeciesGeneratorBuilder(markovChainBuilder)
}
