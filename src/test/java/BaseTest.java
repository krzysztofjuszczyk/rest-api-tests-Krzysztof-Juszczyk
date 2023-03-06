import io.restassured.RestAssured;

public class BaseTest {
    public BaseTest(){
        RestAssured.baseURI = "https://peselvalidatorapitest.azurewebsites.net/api";
    }
}
