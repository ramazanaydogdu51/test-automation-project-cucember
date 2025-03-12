package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        tags = "@POST",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class APITestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false) // API testlerini paralel çalıştırabiliriz
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
