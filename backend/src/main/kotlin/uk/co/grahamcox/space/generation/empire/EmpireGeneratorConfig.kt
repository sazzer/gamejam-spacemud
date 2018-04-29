package uk.co.grahamcox.space.generation.empire

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring config for Empire Generation
 */
@Configuration
class EmpireGeneratorConfig {
    @Bean
    fun empireGenerator() = EmpiresGenerator(
            EmpireSizeGenerator(),
            EmpireSeedGenerator()
    )
}
