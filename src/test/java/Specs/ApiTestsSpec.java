package Specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class ApiTestsSpec {
    public static RequestSpecification standartRequestSpec = with()
            .log().all()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates());


    public static ResponseSpecification standartResponseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }
}
