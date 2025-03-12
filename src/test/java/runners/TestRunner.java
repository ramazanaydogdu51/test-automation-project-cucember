package runners;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class TestRunner extends BaseTestRunner {
}
