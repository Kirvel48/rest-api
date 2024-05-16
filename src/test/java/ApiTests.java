import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void userNotFoundTest() {
        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("/api/users/23")
                .then()
                .assertThat().statusCode(404);
    }


    @Test
    void registerUserSuccessTest() {
        String userData = "{\"password\": \"pistol\", \"email\": \"eve.holt@reqres.in\"}";

        given()
                .log().all()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/register")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    void checkAvatar() {
        given()
                .log().all()
                .filter(withCustomTemplates())
                .when()
                .get("/users?page=2")
                .then()
                .body("data.avatar", not(empty()));
    }


    @Test
    void createUserTest() {
        String userData = "{\"name\": \"Joe\", \"job\": \"actor\"}";
        given()

                .log().all()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/users")
                .then()
                .assertThat().statusCode(201)
                .body("name", is("Joe"));

    }

    @Test
    void updateUserTest() {
        String userData = "{\"name\": \"Joe\", \"job\": \"director\"}";
        given()

                .log().all()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .put("/users/2")
                .then().body("name", is("Joe"));

    }


}

