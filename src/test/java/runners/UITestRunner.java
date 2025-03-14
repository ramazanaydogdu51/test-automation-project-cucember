package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverManager;

@CucumberOptions(
        features = "classpath:features/web",  // UI testlerinin bulunduğu klasör
        glue = "stepDefinitions",
        plugin = {"pretty", "json:target/cucumber-reports/UIReport.json"}
)
public class UITestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    @Parameters({"runTest", "browser", "headless", "useProfile"})
    public void setUp(@Optional("true") String runTest,
                      @Optional("chrome") String browser,
                      @Optional("false") String headless,
                      @Optional("false") String useProfile) {

        if (runTest.equalsIgnoreCase("false")) {
            throw new SkipException("Skipping UI Test as runTest=false");
        }

        System.setProperty("browser", browser);
        System.setProperty("headless", headless);
        System.setProperty("useProfile", useProfile);
        DriverManager.getDriver();
    }
}
