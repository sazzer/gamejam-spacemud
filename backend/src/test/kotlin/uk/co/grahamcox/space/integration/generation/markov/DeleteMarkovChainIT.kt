package uk.co.grahamcox.space.integration.generation.markov

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for deleting Markov Chains
 */
@Sql("classpath:uk/co/grahamcox/space/integration/generation/markov/data.sql")
class DeleteMarkovChainIT : SpringTestBase() {
    @Test
    fun deleteSingleChain() {
        val deleteResponse = requester.delete("/api/empire/markovChains/00000000-0000-0000-0000-000000000001")

        Assertions.assertEquals(HttpStatus.NO_CONTENT, deleteResponse.statusCode)

        val getResponse = requester.get("/api/empire/markovChains/00000000-0000-0000-0000-000000000001")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, getResponse.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/not-found/unknown-id",
                  "detail" : "Unknown ID: 00000000-0000-0000-0000-000000000001",
                  "status" : 404,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/not-found",
                  "title" : "Markov Chain not found"
                }""", getResponse.body) }
        )
    }

    @Test
    fun deleteUnknownChain() {
        val deleteResponse = requester.delete("/api/empire/markovChains/00000000-0000-0000-0000-000000000000")

        Assertions.assertEquals(HttpStatus.NO_CONTENT, deleteResponse.statusCode)
    }
}
