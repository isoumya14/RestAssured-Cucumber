package CucumberTests.Steps;

import CucumberTests.CucumberUtilis.APIResources;
import CucumberTests.CucumberUtilis.TestDataBuild;
import CucumberTests.CucumberUtilis.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class MyStepdefs extends Utils {

    // Declare variables for request and response specifications, response object, and test data builder
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String place_id;

    // Step definition for adding a place payload with given name, language, and address
    @Given("Add Place Payload with {string}  {string} {string}")
    public void add_Place_Payload_with(String name, String language, String address) throws IOException {
        // Initialize the request specification with base URI and other configurations
        // Set the request body with the payload created using TestDataBuild class
        res = given().spec(requestSpecification())
                .body(data.addPlacePayLoad(name, language, address));
    }

    // Step definition for making an HTTP request with given resource and method
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        // Convert the resource string to APIResources enum to get the resource path
        APIResources resourceAPI = APIResources.valueOf(resource);
        String resourcePath = resourceAPI.getResource();
        System.out.println(resourcePath);

        // Build the response specification with expected status code and content type
        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        // Make the HTTP request based on the method type (POST or GET)
        if (method.equalsIgnoreCase("POST")) {
            response = res.when().post(resourcePath);
        } else if (method.equalsIgnoreCase("GET")) {
            response = res.when().get(resourcePath);
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    // Step definition for verifying the API call success with the expected status code
    @Then("the API call got success with status code {int}")
    public void the_API_call_got_success_with_status_code(Integer int1) {
        // Assert that the response status code matches the expected status code
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    // Step definition for verifying a key-value pair in the response body
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String Expectedvalue) {
        // Assert that the value of the specified key in the response body matches the expected value
        Assert.assertEquals(getJsonPath(response, keyValue), Expectedvalue);
    }

    // Step definition for verifying that the created place ID maps to the expected name using the given resource
    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
        // Extract the place ID from the response
        place_id = getJsonPath(response, "place_id");
        // Initialize the request specification with the place ID as a query parameter
        res = given().spec(requestSpecification()).queryParam("place_id", place_id);
        // Make a GET request to the specified resource
        user_calls_with_http_request(resource, "GET");
        // Extract the actual name from the response and assert that it matches the expected name
        String actualName = getJsonPath(response, "name");
        Assert.assertEquals(actualName, expectedName);
    }

    // Step definition for deleting a place using the place ID
    @Given("DeletePlace Payload")
    public void deleteplace_Payload() throws IOException {
        // Initialize the request specification with the delete place payload created using TestDataBuild class
        res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }
}