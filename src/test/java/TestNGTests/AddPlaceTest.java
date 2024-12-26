package TestNGTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.CreateGoogleLocation;
import pojo.Location;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class AddPlaceTest {
    public static void main(String[] args) {
        CreateGoogleLocation createGoogleLocation = new CreateGoogleLocation();
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        createGoogleLocation.setLocation(location);
        createGoogleLocation.setAccuracy(50);
        createGoogleLocation.setAddress("29, side layout, cohen 09");
        createGoogleLocation.setName("Frontline house");
        createGoogleLocation.setPhone_number("(+91) 983 893 3937");
        String[] types = {"shoe park", "shop"};
        createGoogleLocation.setTypes(Arrays.asList(types));
        createGoogleLocation.setWebsite("http://google.com");
        createGoogleLocation.setLanguage("French-IN");

        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req)
                .body(createGoogleLocation);
        Response response = res.when().post("/maps/api/place/add/json").
                then().spec(resspec).extract().response();
        String responseString = response.asString();
        System.out.println(responseString);

    }
}
