package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "classpath:features/api",
        glue = "stepDefinitions",
        plugin = {"pretty", "json:target/cucumber-reports/APIReport.json"}
)
public class APITestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    @Parameters({"runTest"})
    public void setUp(@Optional("true") String runTest) {
        if (runTest.equalsIgnoreCase("false")) {
            throw new SkipException("Skipping API Test as runTest=false");
        }
    }
}
