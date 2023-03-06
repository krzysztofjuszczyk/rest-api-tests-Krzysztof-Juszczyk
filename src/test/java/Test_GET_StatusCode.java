import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class Test_GET_StatusCode extends BaseTest {
    @Test
        public void testGetRequest_ResponseStatusOK_Code200(){
        String peselValid = "37010865884";
        Response response = get("/Pesel?pesel=" + peselValid);
    }

    @Test
    public void testGetRequest_ResponseStatusCode400() {
        String peselEmpty = "";
        Response response = get("/Pesel?pesel="+peselEmpty);
        Assert.assertEquals(response.statusCode(), 400);
    }


}
