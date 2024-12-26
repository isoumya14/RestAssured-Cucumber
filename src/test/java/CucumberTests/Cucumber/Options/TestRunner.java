package CucumberTests.Cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/CucumberTests/Features",
        glue = "CucumberTests.Steps",
        plugin = {"pretty", "html:target/cucumber-reports","json:target/jsonReports/cucumber-report.json"},
        monochrome = true
       // tags = "@AddPlace"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}