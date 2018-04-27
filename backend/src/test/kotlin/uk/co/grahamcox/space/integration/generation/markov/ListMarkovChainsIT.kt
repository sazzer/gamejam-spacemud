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
class ListMarkovChainsIT : SpringTestBase() {
    @Test
    fun listNoChains() {
        val response = requester.get("/api/empire/markovChains")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { jsonMatch("""{
                  "_links" : {
                    "self" : {
                      "href" : "${uriBuilder().path("/api/empire/markovChains/").toUriString()}",
                      "templated" : false,
                      "type" : "application/hal+json"
                    }
                  },
                  "_embedded" : {
                    "chains" : [ ]
                  },
                  "totalCount" : 0
                }""", response.body) }
        )
    }

    @Test
    @Sql("classpath:uk/co/grahamcox/space/integration/generation/markov/data.sql")
    fun listChains() {
        val response = requester.get("/api/empire/markovChains")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { jsonMatch("""{
                  "_links" : {
                    "self" : {
                      "href" : "${uriBuilder().path("/api/empire/markovChains/").toUriString()}",
                      "templated" : false,
                      "type" : "application/hal+json"
                    }
                  },
                  "_embedded" : {
                    "chains" : [ {
                      "_links" : {
                        "self" : {
                          "href" : "${uriBuilder().path("/api/empire/markovChains/00000000-0000-0000-0000-000000000001").toUriString()}",
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
                    }, {
                      "_links" : {
                        "self" : {
                          "href" : "${uriBuilder().path("/api/empire/markovChains/00000000-0000-0000-0000-000000000002").toUriString()}",
                          "templated" : false,
                          "type" : "application/hal+json"
                        }
                      },
                      "_meta" : {
                        "created" : "2018-03-28T08:29:00Z",
                        "updated" : "2018-03-28T09:29:00Z",
                        "version" : "11111111-1111-1111-1111-111111111111"
                      },
                      "name" : "Second Markov Chain",
                      "type" : "species",
                      "prefix" : 2,
                      "corpus" : [ "ab", "bc", "cd" ]
                    } ]
                  },
                  "totalCount" : 2
                }""", response.body) }
        )
    }
}
