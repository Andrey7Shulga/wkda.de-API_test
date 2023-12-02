package api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Core {

    public <T> T requestAndGetResponseAsClass(
        Class<T> responseClass,
        RequestSpecification rs,
        Object obj,
        String endpoint,
        Method method
    ) {
        return
            given()
                .spec(rs)
                .body(obj == null ? "[]" : obj)
            .when()
                .request(method, endpoint)
            .then()
                .statusCode(200)
                .extract().as(responseClass);
    }

    public <T> T getAndGetResponseAsClass(
        Class<T> responseClass,
        RequestSpecification rs,
        String endpoint
    ) {
        return requestAndGetResponseAsClass(responseClass, rs, null, endpoint, Method.GET);
    }

    public ValidatableResponse commonResponse(
        RequestSpecification rs,
        String token,
        String endpoint,
        Method method
    ) {
        return
            given()
                .spec(rs)
                .header("token", token)
            .when()
                .request(method, endpoint)
            .then();
    }

    public ValidatableResponse responseWithBody(
        RequestSpecification rs,
        String token,
        String endpoint,
        Method method,
        Object obj
    ) {
        return
            given()
                .spec(rs)
                .header("token", token)
                .body(obj)
            .when()
                .request(method, endpoint)
            .then();
    }

    public <T, responseClass> List<T> get_AndGetResponseAsListOfClass(
        Class<T> responseClass,
        RequestSpecification rs,
        String token,
        String endpoint
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, responseClass);
        JsonNode jsonNode =
            given()
                .spec(rs)
                .header("token", token)
            .when()
                .get(endpoint)
            .then()
                .statusCode(200)
                .extract().as(JsonNode.class);
        List<responseClass> list = mapper.readValue(String.valueOf(jsonNode), listType);
        return (List<T>) list;
    }

    public File filePath (String path) {
        return new File(path);
    }
}
