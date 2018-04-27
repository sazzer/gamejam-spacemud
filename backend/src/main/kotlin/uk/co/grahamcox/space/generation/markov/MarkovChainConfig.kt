package uk.co.grahamcox.space.generation.markov

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import uk.co.grahamcox.space.generation.markov.dao.JdbcMarkovChainDaoImpl
import uk.co.grahamcox.space.generation.markov.dao.MarkovChainDao
import uk.co.grahamcox.space.generation.markov.rest.MarkovChainController
import uk.co.grahamcox.space.generation.markov.rest.MarkovChainPageTranslator
import uk.co.grahamcox.space.generation.markov.rest.MarkovChainTranslator
import java.time.Clock

/**
 * Spring Config for working with Markov Chains
 */
@Configuration
class MarkovChainConfig {
    @Bean
    fun markovChainDao(clock: Clock, jdbcTemplate: NamedParameterJdbcTemplate, objectMapper: ObjectMapper) =
            JdbcMarkovChainDaoImpl(clock, jdbcTemplate, objectMapper)

    @Bean
    fun markovChainBuilder(dao: MarkovChainDao) = MarkovChainBuilder(dao)

    @Bean
    fun markovChainController(dao: MarkovChainDao): MarkovChainController {
        val markovChainTranslator = MarkovChainTranslator()
        return MarkovChainController(dao, markovChainTranslator, MarkovChainPageTranslator(markovChainTranslator))
    }
}
