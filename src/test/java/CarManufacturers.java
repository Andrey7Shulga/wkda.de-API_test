import api.client.EndpointURLbuiltDates;
import api.client.EndpointURLmainTypes;
import api.client.EndpointURLmainTypesDetails;
import api.client.EndpointURLmanufacturer;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import pogo.responses.Dates;
import pogo.responses.Manufacturers;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarManufacturers extends BaseTest {

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


    @Test
    @Order(1)
    public void completeRequestTest() {

        response =
            given()
                .spec(reqSpec)
            .when()
                .get(EndpointURLmainTypesDetails.MAIN_TYPES_DETAILS
                        .addPath(String.format("?manufacturer=%s&main-type=%s&built-date=%s&body-type=%s"
                    , manufacturer, mainType, builtDate, bodyType)));

//        Assert.assertEquals(response.jsonPath().get("wkda['" + responseModelOne + "']"), responseModelOneBody);

        Map<String, String> jsonPathEvaulator = response.jsonPath().get("wkda");

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(jsonPathEvaulator.get(responseModelOne)).isEqualTo(responseModelOneBody);
        assertThat(jsonPathEvaulator.get(responseModelTwo)).isEqualTo(responseModelTwoBody);

    }

    @Test
    @Order(2)
    public void manufacturerRequest() {

        Manufacturers manufacturers = core.get_AndGetResponseAsClass(
                Manufacturers.class,
                reqSpec,
                EndpointURLmanufacturer.MANUFACTURER.getPath());

        assertThat(manufacturers.wkda.get(manufacturer)).isEqualTo(manufacturerTitle);


    }

    @Test
    @Order(3)
    public void maintypesManufacturerRequest() {

        Manufacturers manufacturers = core.get_AndGetResponseAsClass(
                Manufacturers.class,
                reqSpec,
                EndpointURLmainTypes.MAIN_TYPES.addPath(String.format("?manufacturer=%s", manufacturer)));

        assertThat(manufacturers.totalPageCount).isEqualTo(1);
        assertThat(manufacturers.wkda.get(mainType)).isEqualTo(mainType);

    }

    @Test
    @Order(4)
    public void builtDatesRequest() {

        Dates dates = core.get_AndGetResponseAsClass(
                Dates.class,
                reqSpec,
                EndpointURLbuiltDates.BUILT_DATES.addPath(String.format("?"
                        + EndpointURLmanufacturer.MANUFACTURER.getPath() + "=%s&main-type=%s", manufacturer, mainType)));

        assertThat(dates.wkda.get("2001")).isEqualTo("2001");

    }

    @Test
    @Order(5)
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
