package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import utils.DriverManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)


public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver(); // Tüm testlerden sonra WebDriver'ı kapat
    }


}
