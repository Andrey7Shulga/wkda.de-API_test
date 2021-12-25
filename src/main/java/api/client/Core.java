package api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class Core {

    private RequestSenderOptions rso;

    public   <T> T post_AndGetResponseAsClass(Class<T> responseclass, RequestSpecification rs, Object obj, String endpoint) {
        return
                given()
                        .spec(rs)
                        .body(obj)
                .when()
                        .post(endpoint)
                .then()
                        .statusCode(200)
                        .extract().as(responseclass);
    }

    public   <T> T get_AndGetResponseAsClass(Class<T> responseclass, RequestSpecification rs, String endpoint) {
        return
                given()
                        .spec(rs)
                .when()
                        .get(endpoint)
                .then()
                        .statusCode(200)
                        .extract().as(responseclass);
    }



    public void assertResponseBodyLine(Response response, String key, Object value) {

        JsonPath jsonPath = response.jsonPath();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(jsonPath.get(key).equals(value));

    }

    public Response get_getStandartResponse(RequestSpecification rs, String token, String endpoint) {
        return
                given()
                        .spec(rs)
                        .header("token", token)
                .when()
                        .get(endpoint);

    }

    public Response post_getStandartResponse(RequestSpecification rs, String token, String endpoint) {
        return
                given()
                        .spec(rs)
                        .header("token", token)
                .when()
                        .post(endpoint);

    }

    public Response delete_getStandartResponse(RequestSpecification rs, String token, String endpoint) {
        return
                given()
                        .spec(rs)
                        .header("token", token)
                .when()
                        .delete(endpoint);

    }

    public Response get_getBodyResponse (RequestSpecification rs, String token, String endpoint, Object obj) {

        return
                given()
                        .spec(rs)
                        .header("token", token)
                        .body(obj)
                        .when()
                        .get(endpoint);

    }

    public Response post_getBodyResponse (RequestSpecification rs, String token, String endpoint, Object obj) {
        return
                given()
                        .spec(rs)
                        .header("token", token)
                        .body(obj)
                        .when()
                        .post(endpoint);

    }

    public <T, responseclass> List<T> get_AndGetResponseAsListOfClass
            (Class<T> responseclass, RequestSpecification rs, String token, String endpoint) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, responseclass);

        JsonNode jsonNode =
                given()
                        .spec(rs)
                        .header("token", token)
                        .when()
                        .get(endpoint)
                        .then()
                        .statusCode(200)
                        .extract().as(JsonNode.class);

        List<responseclass> list = mapper.readValue(String.valueOf(jsonNode), listType);
        return (List<T>) list;

    }

    public File filePath (String path) {
        return new File(path);
    }

    public void jsonResponceResearchAssertKeyValue(Response resp, String key, String value, boolean bln) {

        JsonPath jsonPathEvaulator = resp.jsonPath();
        List<Map<String, ?>> jsonResponseRoot = jsonPathEvaulator.getList("$." + key);

        for (Map<String, ?> i : jsonResponseRoot) {
            Assert.assertTrue(i.containsKey(key));
            Assert.assertEquals(i.containsValue(value), bln);
        }
    }

    public void jsonResponceAssertKeyValue(Response resp, String path, String key, String value) {
        String jsonPathEvaulator = resp.jsonPath().getString(path);
        Assert.assertTrue(jsonPathEvaulator.contains(key + ":" + value));

    }

}
