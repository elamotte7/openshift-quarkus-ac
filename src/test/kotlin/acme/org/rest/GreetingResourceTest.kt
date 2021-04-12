package acme.org.rest

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.keycloak.representations.AccessTokenResponse

@QuarkusTest
@QuarkusTestResource(KeycloakServer::class)
class GreetingResourceTest {

    companion object {
        init {
            RestAssured.useRelaxedHTTPSValidation()
        }
    }

    @Test
    fun testHelloEndpoint() {
        RestAssured.given()
            .`when`()["/greeting"]
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("Hello openshift"))
    }
}