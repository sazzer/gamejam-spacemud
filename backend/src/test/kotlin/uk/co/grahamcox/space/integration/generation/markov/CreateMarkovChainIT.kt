package uk.co.grahamcox.space.integration.generation.markov

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for creating Markov Chains
 */
@Sql("classpath:uk/co/grahamcox/space/integration/generation/markov/data.sql")
class CreateMarkovChainIT : SpringTestBase() {
    @Test
    fun createChain() {
        val updateResponse = requester.post("/api/empire/markovChains",
                mapOf(
                        "name" to "New Name",
                        "type" to "planets",
                        "prefix" to 1,
                        "corpus" to listOf("ab", "cd", "ef")
                ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.CREATED, updateResponse.statusCode) },

                Executable { jsonMatch("""{
                  "_links" : {
                    "self" : {
                      "href" : "${updateResponse.bodyValue("_links.self.href")}",
                      "templated" : false,
                      "type" : "application/hal+json"
                    }
                  },
                  "_meta" : {
                    "created" : "${updateResponse.bodyValue("_meta.created")}",
                    "updated" : "${updateResponse.bodyValue("_meta.updated")}",
                    "version" : "${updateResponse.bodyValue("_meta.version")}"
                  },
                  "name" : "New Name",
                  "type" : "planets",
                  "prefix" : 1,
                  "corpus" : [ "ab", "cd", "ef" ]
                }""", updateResponse.body) }
        )

        val getResponse = requester.get(updateResponse.bodyValue("_links.self.href").toString())

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, getResponse.statusCode) },

                Executable { Assertions.assertEquals(updateResponse.body, getResponse.body) }
        )
    }

    @Test
    fun createChainDuplicateName() {
        val response = requester.post("/api/empire/markovChains",
                mapOf(
                        "name" to "Second Markov Chain",
                        "type" to "species",
                        "prefix" to 2,
                        "corpus" to listOf("ab", "cd", "ef")
                ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.CONFLICT, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/duplicate/duplicate-name",
                  "detail" : "Duplicate name",
                  "status" : 409,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/empire/markov_chains/problems/duplicate",
                  "title" : "Duplicate Markov Chain details"
                }""", response.body) }
        )
    }
}
