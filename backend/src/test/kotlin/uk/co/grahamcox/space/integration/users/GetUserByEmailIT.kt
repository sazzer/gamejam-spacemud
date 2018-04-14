package uk.co.grahamcox.space.integration.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for getting a user by Email Address
 */
@Sql("classpath:uk/co/grahamcox/space/integration/users/GetUserByEmail.sql")
class GetUserByEmailIT : SpringTestBase() {
    @Test
    fun getUnknownUser() {
        val response = requester.get("/api/users/emails/unknown@example.com")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found/unknown-email",
                  "detail" : "Unknown email address: unknown@example.com",
                  "status" : 404,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found",
                  "title" : "User not found"
                }""", response.body) }
        )
    }

    @Test
    fun getKnownUser() {
        val response = requester.get("/api/users/emails/test@user.example.com")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { jsonMatch("""{
                  "_links" : {
                    "self" : {
                      "href" : "${uriBuilder().path("/api/users/00000000-0000-0000-0000-000000000001").toUriString()}",
                      "templated" : false,
                      "type" : "application/hal+json"
                    }
                  },
                  "_meta" : {
                    "created" : "2018-03-28T08:29:00Z",
                    "updated" : "2018-03-28T09:29:00Z",
                    "version" : "11111111-1111-1111-1111-111111111111"
                  },
                  "email" : "test@user.example.com",
                  "displayName" : "Test User"
                }""", response.body) }
        )
    }
}
