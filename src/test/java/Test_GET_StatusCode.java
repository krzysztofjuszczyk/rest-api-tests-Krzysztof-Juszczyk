import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;

public class Test_GET_StatusCode extends BaseTest {

    @DataProvider(name = "validPeselsFromFile")
    public static Object[][] getFileData() throws IOException {
        String filename = "src/test/resources/validPeselRandom.csv"; // replace with your filename
        List<Object[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length ; i++) {
                    Object[] record = new Object[2];
                    record[0] = values[i].trim(); // pesel value
                    record[1] = ""; // additional value - not used currently
                    records.add(record);
                }


            }
        }
        return records.toArray(new Object[0][0]);
    }


// CODE 200 - 3 ways of testing

    @Test
        public void testGetRequest_ResponseStatusOK_Code200(){
        String peselValid = "37010865884";
        Response response = get("/Pesel?pesel=" + peselValid);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.body(),"");
    }

    @Test (dataProvider = "valid", dataProviderClass = DPClass.class)
    public void testGetRequest_ResponseStatusOK_Code200_DP(String pesel, String expectedGender, String expectedDate){

        Response response = get("/Pesel?pesel=" + pesel);
        boolean isValid = response.path("isValid");
        String actualGender = response.path("gender");
        String actualDateFull = response.path("dateOfBirth"); //T00:00:00
        String actualDate = actualDateFull.substring(0,actualDateFull.length()-9); // removed hour
//          when pesel is valid:  "errors": []
        List<Object> errors = response.path("errors");
        boolean errorsIsEmpty = errors.isEmpty();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(isValid);
        Assert.assertEquals(actualGender, expectedGender);
        Assert.assertEquals(actualDate, expectedDate);
        Assert.assertTrue(errorsIsEmpty);
        if (errorsIsEmpty){
            System.out.println(pesel+" error list is empty " + actualDate + " " + actualGender);
        }

        //2nd version of veryfing that list of errors is empty
        List<Object> errors2 = response.path("errors");
        try {
            assertThat(errors2, Matchers.empty());
            System.out.println(pesel + " no errors");
        } catch (AssertionError e) {
            System.out.println(pesel + " has errors");
        }

    }


    @Test (dataProvider = "validPeselsFromFile")
    public void testGetRequest_ResponseStatusOK_Code200_FromFile(String pesel, String notUsed){
// I don't know how to use String[] in DataProviders
        Response response = get("/Pesel?pesel=" + pesel);
        System.out.println(pesel);  // for testing
        Assert.assertEquals(response.statusCode(),200);
    }


    // CODE 400
    @Test
    public void testGetRequest_ResponseStatusCode400() {
        String peselEmpty = "";
        Response response = get("/Pesel?pesel="+peselEmpty);
        Assert.assertEquals(response.statusCode(), 400);
    }
}
