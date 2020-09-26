import api.client.Core;
import api.client.Requests;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.Map;


public class BaseTest extends Requests {

    protected Response response;
    protected RequestSpecification reqSpec;
    protected Core core;

    public BaseTest () {

        core = new Core();
    }

    @BeforeAll
    void setup () {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        reqSpec = baseRequestSpec();


    }




}
