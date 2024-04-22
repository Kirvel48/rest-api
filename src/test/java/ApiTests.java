import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void checkTotal() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .body("total", is(12));
    }


    @Test
    void checkSuccessStatusCodeTotal() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    void checkAvatar() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .body("data.avatar", not(empty()));
    }


    @Test
    void CreateUserTest() {
        String userData = "{\"name\": \"Joe\", \"job\": \"actor\"}";
        given()

                .log().body()
                .log().params()
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/users")
                .then()
                .assertThat().statusCode(201)
                .body("name", is("Joe"));

    }

    @Test
    void UpdateUserTest() {
        String userData = "{\"name\": \"Joe\", \"job\": \"director\"}";
        given()

                .log().body()
                .log().params()
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .put("/users/2")
                .then()
                .assertThat().statusCode(200)
                .body("name", is("Joe"));

    }


}

