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

    public void jsonResponceRootResearchAssertions(Response resp) {

        JsonPath jsonPathEvaulator = resp.jsonPath();
        List<Map<String, String>> jsonResponseRoot = jsonPathEvaulator.getList("$");

        for (Map<String, String> i : jsonResponseRoot) {
            Assert.assertTrue(i.containsKey("id"));
            Assert.assertTrue(i.containsKey("researchId"));
            Assert.assertTrue(i.containsKey("notationId"));
            Assert.assertTrue(i.containsKey("notationName"));
            Assert.assertTrue(i.containsKey("researchName"));
            Assert.assertTrue(i.containsKey("department"));
            Assert.assertTrue(i.containsKey("created"));
            Assert.assertTrue(i.containsKey("updated"));
            Assert.assertTrue(i.containsKey("file"));
            Assert.assertTrue(i.containsKey("result"));

        }

        System.out.println("The researches' number is: " + jsonResponseRoot.size());

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
//        System.out.println("Json is: " + jsonPathEvaulator.toString());
        Assert.assertTrue(jsonPathEvaulator.contains(key + ":" + value));

    }

}
