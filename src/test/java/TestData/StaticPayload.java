package TestData;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StaticPayload {
    @Test
    public void AddPlace() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("src/test/java/TestData/MapData.json")))).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response); //for parsing Json
        String placeId = js.getString("place_id");
        System.out.println(placeId);
    }
}
