import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;


public class Test_GET_Invalid_Pesel extends BaseTest {

    //"INVL", "Invalid length. Pesel should have exactly 11 digits.
    @Test(dataProvider = "invalidPeselsList_Length", dataProviderClass = DPClass.class)
    public void testGetRequest_Length(String pesel, String comment) {
        // length <11
        Response response = get("/Pesel?pesel=" + pesel);
        boolean isValid = response.path("isValid");
        String actualErrorCode = response.path("errors[0].errorCode");
        System.out.println(pesel + " " + pesel.length() + " errorCode: " + actualErrorCode);
        Assert.assertFalse(isValid);
        Assert.assertEquals(actualErrorCode, "INVL");
    }

    //        "INVC", "Check sum is invalid. Check last digit."             DONE + automatically generated incorrect last digit
    @Test(dataProvider = "invalidPesel_InvalidControlNumber", dataProviderClass = DPClass.class)
    public void testGetRequest_ControlNumber(String pesel, String comment) {
        Response response = get("/Pesel?pesel=" + pesel);
        int actualCode = response.statusCode();
        String actualErrorCode = response.path("errors[0].errorCode");

        Assert.assertEquals(actualCode, 200);
        Assert.assertEquals(actualErrorCode, "INVC");
        System.out.println(pesel + " error:" + actualErrorCode + " status code:" + actualCode);
    }

    //        "INVC" automatically generated incorrect last digit
    @Test(dataProvider = "invalidPesel_InvalidControlNumber_GeneratedAutomaticallyFromValid",
            dataProviderClass = DPClass.class)
    public void testGetRequest_ControlNumberINV(String pesel, String gender, String birthDate) {
        Response response = get("/Pesel?pesel=" + pesel);
        int actualCode = response.statusCode();
        String actualErrorCode = response.path("errors[0].errorCode");

        Assert.assertEquals(actualCode, 200);
        Assert.assertEquals(actualErrorCode, "INVC");
        System.out.println(pesel + " error:" + actualErrorCode + " status code:" + actualCode);
    }

    //        "NBRQ", "Invalid characters. Pesel should be a number."
    @Test(dataProvider = "invalidCharacters", dataProviderClass = DPClass.class)
    public void testGetRequest_InvalidCharacters(String pesel, String comment) {
        Response response = get("/Pesel?pesel=" + pesel);
        int actualCode = response.statusCode();
        Assert.assertEquals(actualCode, 200);
        String actualErrorCode = response.path("errors[0].errorCode");
//        System.out.println(pesel + " " + actualCode + " " + actualErrorCode);

        Assert.assertEquals(actualErrorCode, "NBRQ");
    }

    //        "INVY", "Invalid year." && "INVM" , "Invalid month"
    @Test(dataProvider = "invalidYearAndMonth", dataProviderClass = DPClass.class)
    public void testGetRequest_invalidYear(String pesel, String comment) {
        Response response = get("/Pesel?pesel=" + pesel);
        int actualCode = response.statusCode();
        Assert.assertEquals(actualCode, 200);

        String codeYear = "INVY";
        String codeMonth = "INVM";
        String body = response.getBody().asString();
        boolean hasCorrectCodeYear = body.contains(codeYear);
        boolean hasCorrectCodeMonth = body.contains(codeMonth);

        Assert.assertTrue(hasCorrectCodeYear);
        Assert.assertTrue(hasCorrectCodeMonth);
    }

    //        "INVD", "Invalid day."
    @Test(dataProvider = "invalidDay", dataProviderClass = DPClass.class)
    public void testGetRequest_invalidDay(String pesel, String comment) {
        Response response = get("/Pesel?pesel=" + pesel);
        int actualCode = response.statusCode();
        Assert.assertEquals(actualCode, 200);

        String code = "INVD";
        String body = response.getBody().asString();
        System.out.println(body);
        boolean hasCorrectCode = body.contains(code);

        Assert.assertTrue(hasCorrectCode);
    }
}
