package CucumberTests.Steps;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {        //execute this code only when place id is null
        //write a code that will give you place id

        MyStepdefs myStepdefs = new MyStepdefs();
        if (MyStepdefs.place_id == null) {
            myStepdefs.add_Place_Payload_with("Shetty", "French", "Asia");
            RestAssured.useRelaxedHTTPSValidation();
            myStepdefs.user_calls_with_http_request("AddPlaceAPI", "POST");
            myStepdefs.verify_place_Id_created_maps_to_using("Shetty", "getPlaceAPI");
        }


    }
}
