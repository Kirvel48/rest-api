package Specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class ApiTestsSpec {
    public static RequestSpecification userNotFoundRequest = with()
            .log().all()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates());

    public static RequestSpecification registerUserSuccessRequest = with()
            .log().all()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates());

    public static ResponseSpecification registerUserSuccessResponse = (ResponseSpecification) new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();


    public static RequestSpecification checkAvatarRequest = with()
            .log().all()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates());

    public static RequestSpecification createUserTestRequest = with()
            .log().all()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates());

    public static ResponseSpecification createUserTestResponse = (ResponseSpecification) new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.ALL)
            .build();
    public static RequestSpecification updateUserTestRequest = with()
            .log().all()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates());

    public static ResponseSpecification updateUserTestResponse = (ResponseSpecification) new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();
}