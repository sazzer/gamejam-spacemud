package uk.co.grahamcox.space.integration.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for creating a new user
 */
@Sql("classpath:uk/co/grahamcox/space/integration/users/CreateUser.sql")
class CreateUserIT : SpringTestBase() {
    /**
     * Test successfully creating a new user
     */
    @Test
    fun createUserSuccess() {
        val response = requester.post(uri = "/api/users",
                body = mapOf(
                        "email" to "new@example.com",
                        "displayName" to "New User",
                        "password" to "myPassword"
                ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.CREATED, response.statusCode) },

                Executable { Assertions.assertEquals("new@example.com", response.bodyValue("email")) },
                Executable { Assertions.assertEquals("New User", response.bodyValue("displayName")) },

//                Executable { Assertions.assertNotNull(response.bodyValue("_embedded.token.access_token"))},
//                Executable { Assertions.assertEquals("Bearer", response.bodyValue("_embedded.token.token_type")) },
//                Executable { Assertions.assertNotNull(response.bodyValue("_embedded.token.expires_in")) },

                Executable { Assertions.assertNotNull(response.bodyValue("_links.self.href"))},
                Executable { Assertions.assertEquals(false, response.bodyValue("_links.self.templated")) },
                Executable { Assertions.assertEquals("application/hal+json", response.bodyValue("_links.self.type")) },

                Executable { Assertions.assertNotNull(response.bodyValue("_meta.created"))},
                Executable { Assertions.assertNotNull(response.bodyValue("_meta.updated"))},
                Executable { Assertions.assertNotNull(response.bodyValue("_meta.version"))}

                /*
                {
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
                  "displayName" : "Test User",
                  "_embedded": {
                    "token": {
                      "access_token": "someAccessToken",
                      "token_type": "Bearer",
                      "expires_in": 3600
                    }
                  }
                }
                */
        )
    }

    /**
     * Test successfully creating a new user and then looking it up to see if it really exists
     */
    @Test
    fun lookupCreatedUser() {
        requester.post(uri = "/api/users",
                body = mapOf(
                        "email" to "new@example.com",
                        "displayName" to "New User",
                        "password" to "myPassword"
                ))

        val response = requester.get("/api/users/emails/new@example.com")

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { Assertions.assertEquals("new@example.com", response.bodyValue("email")) },
                Executable { Assertions.assertEquals("New User", response.bodyValue("displayName")) },

                Executable { Assertions.assertNotNull(response.bodyValue("_links.self.href"))},
                Executable { Assertions.assertEquals(false, response.bodyValue("_links.self.templated")) },
                Executable { Assertions.assertEquals("application/hal+json", response.bodyValue("_links.self.type")) },

                Executable { Assertions.assertNotNull(response.bodyValue("_meta.created"))},
                Executable { Assertions.assertNotNull(response.bodyValue("_meta.updated"))},
                Executable { Assertions.assertNotNull(response.bodyValue("_meta.version"))}
        )
    }

    /**
     * Test failing to create a new user because the email address already existed
     */
    @Test
    fun createUserDuplicateEmail() {
        val response = requester.post(uri = "/api/users",
                body = mapOf(
                        "email" to "test@user.example.com",
                        "displayName" to "New User",
                        "password" to "myPassword"
                ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.CONFLICT, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/duplicate/duplicate-email",
                  "detail" : "Duplicate email address",
                  "status" : 409,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/duplicate",
                  "title" : "Duplicate User Details"
                }""", response.body) }
        )
    }
}
