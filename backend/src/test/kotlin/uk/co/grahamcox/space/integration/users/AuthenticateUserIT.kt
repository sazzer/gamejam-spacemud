package uk.co.grahamcox.space.integration.users

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import uk.co.grahamcox.space.spring.SpringTestBase

/**
 * Integration tests for Authenticating a user
 */
@Sql("classpath:uk/co/grahamcox/space/integration/users/AuthenticateUser.sql")
class AuthenticateUserIT : SpringTestBase() {
    /**
     * Test successfully authenticating the user
     */
    @Test
    fun authenticateSuccess() {
        val response = requester.post("/api/authenticate", mapOf(
                "email" to "test@user.example.com",
                "password" to "pa55word"
        ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.OK, response.statusCode) },

                Executable { Assertions.assertEquals("test@user.example.com", response.bodyValue("email")) },
                Executable { Assertions.assertEquals("Test User", response.bodyValue("displayName")) },

                Executable { Assertions.assertNotNull(response.bodyValue("_embedded.token.access_token"))},
                Executable { Assertions.assertEquals("Bearer", response.bodyValue("_embedded.token.token_type")) },
                Executable { Assertions.assertNotNull(response.bodyValue("_embedded.token.expires_in")) },

                Executable { Assertions.assertEquals(uriBuilder().path("/api/users/00000000-0000-0000-0000-000000000001").toUriString(),
                        response.bodyValue("_links.self.href"))},
                Executable { Assertions.assertEquals(false, response.bodyValue("_links.self.templated")) },
                Executable { Assertions.assertEquals("application/hal+json", response.bodyValue("_links.self.type")) },

                Executable { Assertions.assertEquals("2018-03-28T08:29:00Z", response.bodyValue("_meta.created"))},
                Executable { Assertions.assertEquals("2018-03-28T09:29:00Z", response.bodyValue("_meta.updated"))},
                Executable { Assertions.assertEquals("11111111-1111-1111-1111-111111111111", response.bodyValue("_meta.version"))}
        )
    }

    /**
     * Test authenticating an unknown user
     */
    @Test
    fun authenticateUnknownUser() {
        val response = requester.post("/api/authenticate", mapOf(
                "email" to "unknown@user.example.com",
                "password" to "pa55word"
        ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found/unknown-email",
                  "detail" : "Unknown email address: unknown@user.example.com",
                  "status" : 404,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/not-found",
                  "title" : "User not found"
                }""", response.body) }
        )
    }

    /**
     * Test authenticating with the wrong password
     */
    @Test
    fun authenticateWrongPassword() {
        val response = requester.post("/api/authenticate", mapOf(
                "email" to "test@user.example.com",
                "password" to "incorrect"
        ))

        Assertions.assertAll(
                Executable { Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode) },

                Executable { jsonMatch("""{
                  "instance" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/authentication/invalid-password",
                  "detail" : "Invalid Password",
                  "status" : 400,
                  "type" : "tag:grahamcox.co.uk,2018,spacemud/users/problems/authentication",
                  "title" : "Authentication Failure"
                }""", response.body) }
        )
    }
}
