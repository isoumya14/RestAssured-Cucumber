package TestNGTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JIRACreateIssue {
    public static void main(String[] args) {
        String AuthToken = "Basic c3JwYWl0YWxAZ21haWwuY29tOkFUQVRUM3hGZkdGMFhXbDIxZmZETGR5MVEza01ERVRFMWpIb1Z5UmJaUzNMbTFwV0FKekdNS2NUZmtXWmJ0X0hrSmZUSVJsVUVkYks1MklYU0F1bGRmeDUyaWNKYUpZZjd2U05jR19wSlBrNUdDQTE4bDVqODE1Qnp6UDlTQWw4bGdiSnFfa0FHeHJOakc5WGo5bjUzT3hfTXlYSG5TcS1fakZqWE84Wm1mTGFkWkhWSUREcmJxST1BNTkzMUVBNQ==";
        RestAssured.baseURI = "https://soumyapaital.atlassian.net/";
//        String createIssueResponse = given().header("Content-Type", "application/json").
//                header("Authorization", AuthToken).
//                body("{\n" + "    \"fields\": {\n" + "       \"project\":\n" + "       {\n" + "          \"key\": \"SCRUM\"\n" + "       },\n" + "       \"summary\": \"Website items are not working- automation Rest Assured\",\n" + "       \"issuetype\": {\n" + "          \"name\": \"Bug\"\n" + "       }\n" + "   }\n" + "}").
//                log().all().post("rest/api/3/issue").then().log().all().assertThat().
//                statusCode(201).contentType("application/json").
//                extract().response().asString();
//        JsonPath js = new JsonPath(createIssueResponse);
//        String issueId = js.getString("id");
//        System.out.println(issueId);

        given().pathParam("key", 10009).header("X-Atlassian-Token", "no-check").
                header("Authorization", AuthToken).
                multiPart("file",
                        new File("/Users/H519383/Documents/Develop-Workspace/APIAutomation-RestAssured/src/test/resources/TestData/ToUploadDemo.jpeg")).log().all().post("rest/api/3/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }
}
