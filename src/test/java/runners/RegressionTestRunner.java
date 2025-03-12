package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        tags = "@Regression",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false) // Büyük testlerde paralel çalıştırılabilir
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
