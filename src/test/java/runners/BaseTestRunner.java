package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import utils.DriverManager;

public abstract class BaseTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setupAllure() {
        System.setProperty("allure.results.directory", "target/allure-results");
    }

    @Override
    @DataProvider(parallel = false) // Paralel çalıştırmayı etkinleştir
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver(); // Tüm testlerden sonra WebDriver'ı kapat
    }
}
