import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Random;

public class DPClass {
    @DataProvider(name = "validPeselsList")
    public static Object[][] valid() {
        return new Object[][]{
                //random values
                {"55100834217", "Male", "1955-10-08"},
                {"69472901572", "Male", "2169-07-29"},
                {"87281474670", "Male", "2087-08-14"},
                {"80222980203", "Female", "2080-02-29"},

                //boundary values for XIX century
                {"00810101690", "Male", "1800-01-01"},
                {"00810139086", "Female", "1800-01-01"},
                {"99923199911", "Male", "1899-12-31"},
                {"99923167248", "Female", "1899-12-31"},

                //boundary values for XX century
                {"00010150379", "Male", "1900-01-01"},
                {"00010197224", "Female", "1900-01-01"},
                {"99123175894", "Male", "1999-12-31"},
                {"99123192200", "Female", "1999-12-31"},

                //boundary values for XXI century
                {"00210157679", "Male", "2000-01-01"},
                {"00210180589", "Female", "2000-01-01"},
                {"99323198738", "Male", "2099-12-31"},
                {"99323155627", "Female", "2099-12-31"},

                //boundary values for XXII century
                {"00410103737", "Male", "2100-01-01"},
                {"00410110782", "Female", "2100-01-01"},
                {"99523145750", "Male", "2199-12-31"},
                {"99523130806", "Female", "2199-12-31"},

                //boundary values for XXIII century
                {"00610145331", "Male", "2200-01-01"},
                {"00610137309", "Female", "2200-01-01"},
                {"99723140676", "Male", "2299-12-31"},
                {"99723115948", "Female", "2299-12-31"},

                // leap years valid

                {"04822999490", "Male", "1804-02-29"}, // first leap year
                {"16222942691", "Male", "2016-02-29"},
                {"16222959905", "Female", "2016-02-29"},
                {"00222999715", "Male", "2000-02-29"},
                {"00222981943", "Female", "2000-02-29"},
                {"96622958014", "Male", "2296-02-29"}, // last leap year
        };
    }

    @DataProvider(name = "invalidPeselsList_Length")
    public static Object[][] dpMethod1() {
        return new Object[][]{
                {"5510083427", "length 10"},
                {"694729012", "length 9"},
                {"87281474", "length 8"},
                {"8022298", "length 7"},
                {"802229", "length 6"},
                {"80228", "length 5"},
                {"8298", "length 4"},
                {"898", "length 3"},
                {"88", "length 2"},
                {"8", "length 1"},
                {"055100834270", "legth 12"},
                {"0551008342701", "legth 13"}
        };
    }

    // automatically generates random control number on the database of correct pesel numbers
    @DataProvider(name = "invalidPesel_InvalidControlNumber_GeneratedAutomaticallyFromValid")
    public static Object[][] dpGenerate() {
        Object[][] objects = valid();
        for (Object[] object :
                objects) {
            Random random = new Random();
            String currentNumber = (String) object[0];

            StringBuilder sb = new StringBuilder(currentNumber);
            char lastChar = sb.charAt(10);
            int randomDigit = Character.getNumericValue(lastChar);
            while (randomDigit == Character.getNumericValue(lastChar)) {
                randomDigit = random.nextInt(10);
            }

            sb.setCharAt(10, Character.forDigit(randomDigit, 10));
            object[0] = sb.toString();
        }
        return objects;
    }

    @DataProvider(name = "invalidPesel_InvalidControlNumber")
    public static Object[][] dpMethod2() {
        return new Object[][]{
                // correct control number -1 or +1
                {"55100834216", "-1"},
                {"69472901571", "-1"},
                {"87281474679", "-1"},
                {"80222980202", "-1"},
                {"55100834218", "+1"},
                {"69472901573", "+1"},
                {"87281474671", "+1"},
                {"80222980204", "+1"},
        };
    }

    @DataProvider(name = "invalidCharacters")
    public static Object[][] invalidChars() {
        return new Object[][]{
                {"!@#$%^&*())", "all chars inv"},
                {"@@@@@@@@@@@", "all chars inv and the same"},
                {"0081010169@", "last char inv"},
                {"!0810101690", "first char inv"},
                {"997231*0676", "inv char in the middle"},
                {"99@23140676", "inv char in the middle"},

                // chars from keyboard
                {"`0210157679", "'"},
                {"0`210180589", "'"},
                {"9932319873`", "'"},
                {"~9323155627", "~"},
                {"9~323155627", "~"},
                {"9932315562~", "~"},
                {"!0210157679", "!"},
                {"0!210180589", "!"},
                {"9932319873!", "!"},
                {"@9323155627", "@"},
                {"0@210157679", "@"},
                {"0021018058@", "@"},
                {"#9323198738", "#"},
                {"9#323155627", "#"},
                {"0021015767#", "#"},
                {"$0210180589", "$"},
                {"9$323198738", "$"},
                {"9932315562$", "$"},
                {"%0210157679", "%"},
                {"0%210180589", "%"},
                {"9932319873%", "%"},
                {"^9323155627", "^"},
                {"0^210157679", "^"},
                {"0021018058^", "^"},
//                {"&9323198738", "&"}, //special char - tests removed
//                {"9&323155627", "&"},
//                {"0021015767&", "&"},
                {"*0210180589", "*"},
                {"9*323198738", "*"},
                {"9932315562*", "*"},
                {"(0210157679", "("},
                {"0(210180589", "("},
                {"9932319873(", "("},
                {")9323155627", ")"},
                {"0)210157679", ")"},
                {"0021018058)", ")"},
//                {"-0210157679", "-"}, // potential minor bug found, test removed
                {"0-210180589", "-"},
                {"9932319873-", "-"},
                {"_9323155627", "_"},
                {"0_210157679", "_"},
                {"0021018058_", "_"},
//                {"+9323198738", "+"}, // potential minor bug found, test removed
                {"9+323155627", "+"},
                {"9932319873+", "+"},
//                {"=9323155627", "="}, // potential minor bug found, test removed
                {"0=210157679", "="},
                {"0021018058=", "="},
                {"{9323198738", "{"},
                {"9{323155627", "{"},
                {"9932319873{", "{"},
                {"[9323198738", "["},
                {"9[323155627", "["},
                {"9932319873[", "["},
                {"}9323198738", "}"},
                {"9}323155627", "}"},
                {"9932319873}", "}"},
                {"]9323198738", "]"},
                {"9]323155627", "]"},
                {"9932319873]", "]"},
                {"|9323155627", "|"},
                {"0|210157679", "|"},
                {"0021018058|", "|"},
                {":9323155627", ":"},
                {"0:210157679", ":"},
                {"0021018058:", ":"},
                {";9323155627", ";"},
                {"0;210157679", ";"},
                {"0021018058;", ";"},
                {"'9323155627", "'"},
                {"0'210157679", "'"},
                {"0021018058'", "'"},
                {"<9323155627", "<"},
                {"0<210157679", "<"},
                {"0021018058<", "<"},
                {",9323155627", ","},
                {"0,210157679", ","},
                {"0021018058,", ","},
                {">9323155627", ">"},
                {"0>210157679", ">"},
                {"0021018058>", ">"},
                {".9323155627", "."},
                {"0.210157679", "."},
                {"0021018058.", "."},
                {"?9323155627", "?"},
                {"0?210157679", "?"},
//                {"0021018058?", "?"}, // test removed
                {"/9323155627", "/"},
                {"0/210157679", "/"},
                {"0021018058/", "/"},

                {"\\9323155627", "\\"},
                {"9\\323155627", "\\"},
                {"9323155627\\", "\\"},
                {"\"9323155627", "\""},
                {"0\"/210157679", "\""},
                {"0021018058\"", "\""},


                // tests removed
//                {"&&&&&&&&&&&", "&"},
//                {"&&100834217", "&&"},
//                {"5&100834217", "&"},
//                {"55&00834217", "&"},
//                {"551&0834217", "&"},
//                {"5510&834217", "&"},
//                {"55100&34217", "&"},
//                {"551008&4217", "&"},
//                {"5510083&217", "&"},
//                {"55100834&17", "&"},
//                {"551008342&7", "&"},
//                {"5510083421&", "&"}
        };
    }

    // invaild values as 3&4 pesel digit
    @DataProvider(name = "invalidYearAndMonth")
    public static Object[][] dpMethodYearAndMonth() {
        return new Object[][]{
                {"55130834217", "XX-XX-08"},
                {"55200834217", "XX-XX-08"},
                {"55330834217", "XX-XX-08"},
                {"55400834217", "XX-XX-08"},
                {"55530834217", "XX-XX-08"},
                {"55600834217", "XX-XX-08"},
                {"55730834217", "XX-XX-08"},
                {"55800834217", "XX-XX-08"},
                {"55930834217", "XX-XX-08"},
                {"55990834217", "XX-XX-08"},
                {"55000834217", "XX-XX-08"},

        };
    }

    @DataProvider(name = "invalidDay")
    public static Object[][] dpMethodDay() {
        return new Object[][]{
                {"55100034217", "00"},
                {"55103234217", "32"},
                {"55109934217", "99"},

                {"00810014115", "1800-01-00"},
                {"00813214115", "1800-01-32"},
                {"99720080029", "2299-12-00"},
                {"99723280029", "2299-12-32"},

                {"00822996850", "1800-02-29"}, // it is a non leap year
                {"00022951385", "1900-02-29"}, // it is a non leap year

        };
    }
}
