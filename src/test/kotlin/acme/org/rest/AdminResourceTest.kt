package acme.org.rest

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.keycloak.representations.AccessTokenResponse

@QuarkusTest
@QuarkusTestResource(KeycloakServer::class)
class AdminResourceTest {
    private val KEYCLOAK_SERVER_URL = System.getProperty("keycloak.url", "https://localhost:8543/auth")
    private val KEYCLOAK_REALM = "quarkus"

    companion object {
        init {
            RestAssured.useRelaxedHTTPSValidation()
        }
    }

    @Test
    fun testHelloEndpoint() {
        RestAssured.given()
            .auth().oauth2(getAccessToken("admin"))
            .`when`()["/api/admin"]
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("granted"))
    }

    private fun getAccessToken(userName: String): String? {
        return RestAssured
            .given()
            .param("grant_type", "password")
            .param("username", userName)
            .param("password", userName)
            .param("client_id", "backend-service")
            .param("client_secret", "secret")
            .`when`()
            .post(KEYCLOAK_SERVER_URL.toString() + "/realms/" + KEYCLOAK_REALM + "/protocol/openid-connect/token")
            .`as`(AccessTokenResponse::class.java).getToken()
    }
}