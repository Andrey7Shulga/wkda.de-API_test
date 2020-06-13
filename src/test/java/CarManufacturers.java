import api.client.EndpointURLbuiltDates;
import api.client.EndpointURLmainTypes;
import api.client.EndpointURLmainTypesDetails;
import api.client.EndpointURLmanufacturer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.*;


public class CarManufacturers {

    private static final String URL_KEY = "http://www.wkda.de/papi/v1/car-types/";
    private final String manufacturer = "107";
    private final String manufacturerTitle = "Bentley";
    private final String mainType = "Azure";
    private final String builtDate = "2007";
    private final String bodyType = "1007";

    private static final String responseModelOne = "6.8 V8 Biturbo:336.00";
    private static final String responseModelOneBody = "Azure 6.8 V8 Biturbo (336 kW / 457 PS)";
    private final String responseModelTwo = "6.8 V8:299.00";
    private final String responseModelTwoBody = "Azure 6.8 V8 (299 kW / 407 PS)";
    private final String newManufacturerID = "603";
    private final String newManufacturerTitle = "GAZ";




    @BeforeAll
    public static void beforeTest() {

        RestAssured.baseURI = URL_KEY;

    }

    @Test
    public void completeRequestTest() {

        given()

        .when()
                .get(EndpointURLmainTypesDetails.MAIN_TYPES_DETAILS.addPath(String
                    .format("?manufacturer=%s&main-type=%s&built-date=%s&body-type=%s"
                    , manufacturer, mainType, builtDate, bodyType)))
        .then()
                .statusCode(HTTP_OK)

                .body(Matchers.hasItems())
                .body("wkda['" + responseModelOne + "']", equalTo(responseModelOneBody))
                .body("wkda['" + responseModelTwo + "']", equalTo(responseModelTwoBody));

    }

    @Test
    public void manufacturerRequest() {

        given()
            .when()
                .get(EndpointURLmanufacturer.MANUFACTURER.getPath())
            .then()
                .statusCode(HTTP_OK)
                .body(Matchers.hasItems())
                .body("wkda." + manufacturer, equalTo(manufacturerTitle));

    }

    @Test
    public void maintypesManufacturerRequest() {

        given()
            .when()
                .get(EndpointURLmainTypes.MAIN_TYPES.addPath(String.format("?manufacturer=%s", manufacturer)))
            .then()
                .statusCode(HTTP_OK)
                .body(Matchers.hasItems())
                .body("totalPageCount", equalTo(1))
                .body("wkda.Azure", equalTo(mainType));

    }

    @Test
    public void builtDatesRequest() {

        given()
            .when()
                .get(EndpointURLbuiltDates.BUILT_DATES.addPath(String.format("?"
                        + EndpointURLmanufacturer.MANUFACTURER.getPath() + "=%s&main-type=%s", manufacturer, mainType)))
            .then()
                .statusCode(HTTP_OK)
                .body(Matchers.hasItems())
                .body("wkda", Matchers.isA(Object.class))
                .body("wkda.2001", equalTo("2001"));


    }

    @Test
    public void newManufacturerPost() {

        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        int statusCode;
        String successCode;

        //add key-value pair
        requestParams.put("wkda." + newManufacturerID, newManufacturerTitle);

        //a header stating the request body is a JSON
        request.header("Content-Type", "application/json");

        //add the JSON to the request body
        request.body(requestParams.toJSONString());

        //post the request
        Response response = request.post(EndpointURLmanufacturer.MANUFACTURER.getPath());

        //get the response status code to assert
        statusCode = response.getStatusCode();
        Assert.assertEquals("201", statusCode);

        //get the response success code to assert
        successCode = response.jsonPath().get("SuccessCode");
        System.out.println("Success code is: " + successCode);
        Assert.assertEquals("Correct Success code was returned", "OPERATION_SUCCESS", successCode);

    }

}
