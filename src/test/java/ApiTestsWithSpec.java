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
        step("Send request", () -> given(userNotFoundRequest)
                .when()
                .get("/api/users/23")
                .then()
                .assertThat().statusCode(404));
    }


    @Test
    @DisplayName("Успешная регистрация пользователя")
    void registerUserSuccessTest() {
        ApiTestsRegisterModel ApiTestRegisterModel = new ApiTestsRegisterModel();
        ApiTestRegisterModel.setEmail("eve.holt@reqres.in");
        ApiTestRegisterModel.setPassword("pistol");

        ApiTestsRegisterResponseModel response = step("Send request", () -> given(registerUserSuccessRequest)
                .body(ApiTestRegisterModel)
                .when()
                .post("/register")
                .then()
                .spec(registerUserSuccessResponse)
                .extract().as(ApiTestsRegisterResponseModel.class));
        step("Check response", () -> {
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

        });
    }


    @Test
    @DisplayName("Наличие фото")
    void checkAvatar() {
        step("Send request", () ->
                given(checkAvatarRequest)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .body("data.avatar", not(empty())));
    }


    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        ApiTestsUserModel ApiTestsModel = new ApiTestsUserModel();
        ApiTestsModel.setName("Joe");
        ApiTestsModel.setJob("actor");
        ApiTestsCreateResponseModel response = step("Send request", () -> given(createUserTestRequest)
                .body(ApiTestsModel)
                .when()
                .post("/users")
                .then()
                .spec(createUserTestResponse)
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

        ApiTestsUpdateResponseModel response = step("Send request", () -> given(updateUserTestRequest)

                .body(ApiTestsModel)
                .when()
                .put("/users/2")
                .then()
                .spec(updateUserTestResponse)
                .extract().as(ApiTestsUpdateResponseModel.class));
        step("Check response", () -> {
            assertEquals("Joe", response.getName());
            assertEquals("director", response.getJob());
        });

    }


}

