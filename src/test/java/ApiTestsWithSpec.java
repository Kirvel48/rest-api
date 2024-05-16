import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Specs.ApiTestsSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTestsWithSpec {


    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @DisplayName("Пользователь не найден")
    void userNotFoundTest() {
        int desiredStatusCode = 404;
        step("Send request", () -> given(standartRequestSpec)
                .when()
                .get("/api/users/23")
                .then()
                .spec(standartResponseSpec(desiredStatusCode)));

    }


    @Test
    @DisplayName("Успешная регистрация пользователя")
    void registerUserSuccessTest() {
        ApiTestsRegisterModel ApiTestRegisterModel = new ApiTestsRegisterModel();
        ApiTestRegisterModel.setEmail("eve.holt@reqres.in");
        ApiTestRegisterModel.setPassword("pistol");
        int desiredStatusCode = 200;

        ApiTestsRegisterResponseModel response = step("Send request", () -> given(standartRequestSpec)
                .body(ApiTestRegisterModel)
                .when()
                .post("/register")
                .then()
                .spec(standartResponseSpec(desiredStatusCode))
                .extract().as(ApiTestsRegisterResponseModel.class));
        step("Check response", () -> {
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

        });
    }


    @Test
    @DisplayName("Наличие фото")
    void checkAvatar() {
        int desiredStatusCode = 200;
        step("Send request", () ->
                given(standartRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(standartResponseSpec(desiredStatusCode))
                        .body("data.avatar", not(empty())));
    }


    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        ApiTestsUserModel ApiTestsModel = new ApiTestsUserModel();
        ApiTestsModel.setName("Joe");
        ApiTestsModel.setJob("actor");
        int desiredStatusCode = 201;
        ApiTestsCreateResponseModel response = step("Send request", () -> given(standartRequestSpec)
                .body(ApiTestsModel)
                .when()
                .post("/users")
                .then()
                .spec(standartResponseSpec(desiredStatusCode))
                .extract().as(ApiTestsCreateResponseModel.class));
        step("Check response", () -> {
            assertEquals("Joe", response.getName());
            assertEquals("actor", response.getJob());

        });
    }

    @Test
    @DisplayName("Редактирование данных пользователя")
    void updateUserTest() {
        ApiTestsUserModel ApiTestsModel = new ApiTestsUserModel();
        ApiTestsModel.setName("Joe");
        ApiTestsModel.setJob("director");
        int desiredStatusCode = 200;


        ApiTestsUpdateResponseModel response = step("Send request", () -> given(standartRequestSpec)

                .body(ApiTestsModel)
                .when()
                .put("/users/2")
                .then()
                .spec(standartResponseSpec(desiredStatusCode))
                .extract().as(ApiTestsUpdateResponseModel.class));
        step("Check response", () -> {
            assertEquals("Joe", response.getName());
            assertEquals("director", response.getJob());
        });

    }
}

