package runners;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        tags = "@UI",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class UITestRunner extends BaseTestRunner {
}
