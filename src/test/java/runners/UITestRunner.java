package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        tags = "@UI",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class UITestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false) // UI testleri paralel çalıştırmak riskli olabilir
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
