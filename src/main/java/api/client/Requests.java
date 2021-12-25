package api.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Requests {

    private final static String BASE_URI = "http://www.wkda.de/papi/v1/car-types/";
    private final static String APP_HEADER = "application/json";

    public RequestSpecification baseRequestSpec() {

        return new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .addHeader("accept", APP_HEADER)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();

    }



}
