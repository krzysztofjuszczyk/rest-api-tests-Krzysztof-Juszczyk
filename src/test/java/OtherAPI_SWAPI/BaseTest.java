package OtherAPI_SWAPI;

import io.restassured.RestAssured;

public class BaseTest {
    public BaseTest(){
        RestAssured.baseURI = "https://swapi.dev/api";
    }

}
