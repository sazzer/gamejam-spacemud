package uk.co.grahamcox.space.integration.generation.markov

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for updating Markov Chains
 */
@Sql("classpath:uk/co/grahamcox/space/integration/generation/markov/data.sql")
class UpdateMarkovChainIT : SpringTestBase() {
    @Test
    fun updateSingleChain() {
        val updateResponse = requester.put("/api/generation/markovChains/00000000-0000-0000-0000-000000000001",
                mapOf(
                        "name" to "New Name",
                        "type" to "planets",
                        "prefix" to 1,
                        "corpus" to listOf("ab", "cd", "ef")
                ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, updateResponse.statusCode) },

                Executable { Assertions.assertNotEquals("2018-03-28T09:29:00Z", updateResponse.bodyValue("_meta.updated")) },
                Executable { Assertions.assertNotEquals("11111111-1111-1111-1111-111111111111", updateResponse.bodyValue("_meta.version")) },

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
                    "updated" : "${updateResponse.bodyValue("_meta.updated")}",
                    "version" : "${updateResponse.bodyValue("_meta.version")}"
                  },
                  "name" : "New Name",
                  "type" : "planets",
                  "prefix" : 1,
                  "corpus" : [ "ab", "cd", "ef" ]
                }""", updateResponse.body) }
        )

        val getResponse = requester.get("/api/generation/markovChains/00000000-0000-0000-0000-000000000001")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, getResponse.statusCode) },

                Executable { Assertions.assertEquals(updateResponse.body, getResponse.body) }
        )
    }

    @Test
    fun updateUnknownChain() {
        val response = requester.put("/api/generation/markovChains/00000000-0000-0000-0000-000000000000",
                mapOf(
                        "name" to "New Name",
                        "type" to "species",
                        "prefix" to 2,
                        "corpus" to listOf("ab", "cd", "ef")
                ))

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

    @Test
    fun updateChainDuplicateName() {
        val response = requester.put("/api/generation/markovChains/00000000-0000-0000-0000-000000000001",
                mapOf(
                        "name" to "Second Markov Chain",
                        "type" to "species",
                        "prefix" to 2,
                        "corpus" to listOf("ab", "cd", "ef")
                ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.CONFLICT, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/generation/markov_chains/problems/duplicate/duplicate-name",
                  "detail" : "Duplicate name",
                  "status" : 409,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/generation/markov_chains/problems/duplicate",
                  "title" : "Duplicate Markov Chain details"
                }""", response.body) }
        )
    }
}
