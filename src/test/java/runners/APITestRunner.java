package runners;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        tags = "@APITest",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class APITestRunner extends BaseTestRunner {
}
