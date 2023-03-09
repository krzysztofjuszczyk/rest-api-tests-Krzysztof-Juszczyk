package OtherAPI_SWAPI;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class Test_GET_StatusCode_SWAPI extends BaseTest {

    @Test (dataProvider = "valid", dataProviderClass = DPClass_SWAPI.class)
    // Test verifies status code and expected value of a certain JSON path in an object
    // Data as of 09.03.2023
    public void testGetRequest_ResponseStatusOK_Code200(String url, String path,String expectedName) {
        Response response = get(url);
        String actualBody = response.getBody().asString();
        System.out.println(actualBody);

        int actualCode = response.getStatusCode();
        Assert.assertEquals(actualCode, 200);

        String actualName = response.path(path);
        Assert.assertEquals(actualName, expectedName);
    }

    // Test verifies status code for non-existing id/url
    @Test (dataProvider = "invalid", dataProviderClass = DPClass_SWAPI.class)
    public void testGetRequest_ResponseStatusNotOK_Code404(String url, String comment){
        Response response = get(url);
        int actualCode = response.statusCode();

        Assert.assertEquals(actualCode, 404);
    }

}
