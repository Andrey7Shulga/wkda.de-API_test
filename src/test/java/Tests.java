import api.client.EndpointURLbuiltDates;
import api.client.EndpointURLmainTypes;
import api.client.EndpointURLmainTypesDetails;
import api.client.EndpointURLmanufacturer;
import createData.AddNewManufacturer;
import org.junit.jupiter.api.*;
import pogo.requests.NewManufacturer;
import pogo.responses.Dates;
import pogo.responses.Manufacturers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests extends BaseTest {

    private static final String manufacturer = "107";
    private static final String manufacturerTitle = "Bentley";
    private static final String mainType = "Azure";
    private static final String builtDate = "2007";
    private static final String bodyType = "1007";

    private static final String newModelID = "603";
    private static final String newModelName = "GAZ";

    private static final String responseModelOne = "6.8 V8 Biturbo:336.00";
    private static final String responseModelOneBody = "Azure 6.8 V8 Biturbo (336 kW / 457 PS)";
    private static final String responseModelTwo = "6.8 V8:299.00";
    private static final String responseModelTwoBody = "Azure 6.8 V8 (299 kW / 407 PS)";


    @Test
    @Order(1)
    public void completeRequestTest() {

        Dates mainTypes = core.get_AndGetResponseAsClass(
                Dates.class,
                reqSpec,
                EndpointURLmainTypesDetails.MAIN_TYPES_DETAILS.addPath(
                        String.format("?manufacturer=%s&main-type=%s&built-date=%s&body-type=%s",
                                manufacturer, mainType, builtDate, bodyType)));

        assertThat(mainTypes.wkda.get(responseModelOne)).isEqualTo(responseModelOneBody);
        assertThat(mainTypes.wkda.get(responseModelTwo)).isEqualTo(responseModelTwoBody);

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
                        + EndpointURLmanufacturer.MANUFACTURER.getPath()
                        + "=%s&main-type=%s", manufacturer, mainType)));

        assertThat(dates.wkda.get("2001")).isEqualTo("2001");

    }

//    @Disabled("Until API is developed")
    @Test
    @Order(5)
    public void newManufacturerPost() {

        AddNewManufacturer anm = new AddNewManufacturer();
        NewManufacturer nm = anm.createNewWKDA(newModelID, newModelName);

        given()
                .spec(reqSpec)
                .body(nm)
        .when()
                .post(EndpointURLmanufacturer.MANUFACTURER.getPath())
        .then()
                .statusCode(200)
                .body("message", is("Successfully added"));

    }

}
