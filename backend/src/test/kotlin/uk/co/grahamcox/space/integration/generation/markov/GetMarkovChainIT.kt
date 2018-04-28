package uk.co.grahamcox.space.integration.generation.markov

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for listing Markov Chains
 */
@Sql("classpath:uk/co/grahamcox/space/integration/generation/markov/data.sql")
class GetMarkovChainIT : SpringTestBase() {
    @Test
    fun getSingleChain() {
        val response = requester.get("/api/generation/markovChains/00000000-0000-0000-0000-000000000001")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { jsonMatch("""{
                  "_links" : {
                    "self" : {
                      "href" : "${uriBuilder().path("/api/generation/markovChains/00000000-0000-0000-0000-000000000001").toUriString()}",
                      "templated" : false,
                      "type" : "application/hal+json"
                    }
                  },
                  "_meta" : {
                    "created" : "2018-03-28T08:29:00Z",
                    "updated" : "2018-03-28T09:29:00Z",
                    "version" : "11111111-1111-1111-1111-111111111111"
                  },
                  "name" : "Example Markov Chain",
                  "type" : "species",
                  "prefix" : 2,
                  "corpus" : [ "ab", "bc", "cd" ]
                }""", response.body) }
        )
    }

    @Test
    fun getUnknownChain() {
        val response = requester.get("/api/generation/markovChains/00000000-0000-0000-0000-000000000000")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/generation/markov_chains/problems/not-found/unknown-id",
                  "detail" : "Unknown ID: 00000000-0000-0000-0000-000000000000",
                  "status" : 404,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/generation/markov_chains/problems/not-found",
                  "title" : "Markov Chain not found"
                }""", response.body) }
        )
    }
}
