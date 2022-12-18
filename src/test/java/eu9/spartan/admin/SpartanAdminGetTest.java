package eu9.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;
import static net.serenitybdd.rest.SerenityRest.*;

@SerenityTest
public class SpartanAdminGetTest {
    @BeforeAll
    public static void init() {
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://44.208.34.43:7000";

    }

    @Test
    public void getAllSpartan() {

        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .when()
                .get("/api/spartans");

        System.out.println(lastResponse().statusCode());
        System.out.println(lastResponse().jsonPath().getString("name"));
    }

    @Test
    public void getAllSpartanAssertion() {

        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id",15)
                .when()
                .get("/api/spartans/{id}");

        Ensure.that("Status code is 200",validatableResponse -> validatableResponse.statusCode(201));
        Ensure.that("content type json",vRes -> vRes.contentType(ContentType.JSON));
        Ensure.that("ID is 15", vRess -> vRess.body("id",is(15)));
    }
}
