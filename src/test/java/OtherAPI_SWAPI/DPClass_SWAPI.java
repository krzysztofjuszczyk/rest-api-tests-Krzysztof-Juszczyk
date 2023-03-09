package OtherAPI_SWAPI;

import org.testng.annotations.DataProvider;

public class DPClass_SWAPI {
    @DataProvider (name = "valid")
    public static Object[][] dpMethod(){
        return new Object[][]{
                {"/planets/1/", "name","Tatooine" },
                {"/planets/2/", "name","Alderaan" },
                {"/planets/59/", "name","Kalee" },
                {"/planets/60/", "name", "Umbara" },
                {"/films/1/", "title", "A New Hope"},
                {"/films/6/", "title", "Revenge of the Sith"},
                {"/starships/2/", "name", "CR90 corvette"},
                {"/starships/75/", "name", "V-wing"},
        };
    }

    @DataProvider (name = "invalid")
    public static Object[][] dpMethod1(){
        //Boundary Values
        return new Object[][]{
                {"/planets/-1/", "BV" },
                {"/planets/0/", "BV" },
                {"/planets/61/", "BV" },
                {"/films/-1/", "BV"},
                {"/films/0/", "BV"},
                {"/films/7/", "BV"},
                {"/starships/-1/", ""},
                {"/starships/0/", ""},
                {"/starships/1/", ""},
                {"/starships/76/", ""},


        };
    }

}
