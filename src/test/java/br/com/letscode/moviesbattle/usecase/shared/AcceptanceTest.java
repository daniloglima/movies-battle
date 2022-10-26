package br.com.letscode.moviesbattle.usecase.shared;

import br.com.letscode.moviesbattle.application.http.controller.facade.SignInResponse;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    private int port;

    private RequestSpecification secureSpecification;

    private RequestSpecification configure(){
        return given()
                .port(this.port)
                .contentType(ContentType.JSON);
    }

    protected RequestSpecification doSecureRequest(){
        return this.secureSpecification;
    }
    protected RequestSpecification doUnsecureRequest(){
        return configure();
    }

    private final String authRequest =
            """
                {
                    "identity":"xyz@email.com",
                    "password":"password"
                }
            """;

    protected void registerAccount(){

        doUnsecureRequest()
                .body(authRequest)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }
    protected void executeLoginWithAccount(){

        var response = doUnsecureRequest()
                .body(authRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(SignInResponse.class);

        this.secureSpecification = configure().header("Authorization", "Bearer " + response.token());
    }

}
