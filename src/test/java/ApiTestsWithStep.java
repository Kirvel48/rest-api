import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTestsWithStep {


    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void userNotFoundTest() {
        step("Send request", () -> given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("/api/users/23")
                .then()
                .assertThat().statusCode(404));
    }


    @Test
    void registerUserSuccessTest() {
        ApiTestsRegisterModel ApiTestRegisterModel = new ApiTestsRegisterModel();
        ApiTestRegisterModel.setEmail("eve.holt@reqres.in");
        ApiTestRegisterModel.setPassword("pistol");

        ApiTestsRegisterResponseModel response = step("Send request", () -> (ApiTestsRegisterResponseModel) given()
                .log().all()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(ApiTestRegisterModel)
                .when()
                .post("/register")
                .then()
                .assertThat().statusCode(200)
                .extract().as(ApiTestsRegisterResponseModel.class));
        step("Check response", () -> {
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

        });
    }


    @Test
    void checkAvatar() {
        step("Send request", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().all()
                        .when()
                        .get("/users?page=2")
                        .then()
                        .body("data.avatar", not(empty())));
    }


    @Test
    void createUserTest() {
        ApiTestsUserModel ApiTestsModel = new ApiTestsUserModel();
        ApiTestsModel.setName("Joe");
        ApiTestsModel.setJob("actor");
        ApiTestsCreateResponseModel response = step("Send request", () -> given()
                .log().all()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(ApiTestsModel)
                .when()
                .post("/users")
                .then()
                .assertThat().statusCode(201)
                .extract().as(ApiTestsCreateResponseModel.class));
        step("Check response", () -> {
            assertEquals("Joe", response.getName());
            assertEquals("actor", response.getJob());

        });
    }

    @Test
    void updateUserTest() {
        ApiTestsUserModel ApiTestsModel = new ApiTestsUserModel();
        ApiTestsModel.setName("Joe");
        ApiTestsModel.setJob("director");

        ApiTestsUpdateResponseModel response = step("Send request", () -> given()

                .log().all()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(ApiTestsModel)
                .when()
                .put("/users/2")
                .then()
                .assertThat().statusCode(200)
                .extract().as(ApiTestsUpdateResponseModel.class));
        step("Check response", () -> {
            assertEquals("Joe", response.getName());
            assertEquals("director", response.getJob());
        });

    }


}

