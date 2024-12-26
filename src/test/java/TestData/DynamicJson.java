package TestData;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().header("Content-Type", "application/json").body(Payloads.AddBook(isbn, aisle)).when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = ReUsableMethods.rawToJson(response);
        Long getId = js.get("id");
        System.out.println(getId);

    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        // Array with 2 rows and 2 columns
        // 1st row: "345", "hap"
        // 2nd row: "346", "hap"
        return new Object[][]{{"hap", "345"}, {"happ", "346"}, {"happy", "347"}};
    }
}
