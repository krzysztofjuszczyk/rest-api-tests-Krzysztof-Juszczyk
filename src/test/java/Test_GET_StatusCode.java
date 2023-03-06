import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.get;

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

    @DataProvider(name = "validPeselsList")
    public static Object[][] dpMethod(){
        return new Object[][]{
                {"55100834217", "a"},
                {"69472901572", "b"},
                {"87281474670", "c"},
                {"80222980203", "d"}
        };
    }

// CODE 200 - 3 ways of testing

    @Test
        public void testGetRequest_ResponseStatusOK_Code200(){
        String peselValid = "37010865884";
        Response response = get("/Pesel?pesel=" + peselValid);
        Assert.assertEquals(response.statusCode(),200);
    }

    @Test (dataProvider = "validPeselsList")
    public void testGetRequest_ResponseStatusOK_Code200_DP(String pesel, String comment){

        Response response = get("/Pesel?pesel=" + pesel);
//        System.out.println(pesel + " "+ comment); // uncomment if needed
        Assert.assertEquals(response.statusCode(),200);
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
