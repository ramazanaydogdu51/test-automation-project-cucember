package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;
import utils.DriverManager;

public abstract class BaseTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeClass
    @Parameters({"testType", "browser"})
    public void setUp(@Optional("UI") String testType, @Optional("chrome") String browser) {
        System.setProperty("browser", browser);  // Browser parametresini sistem değişkenine ekle
        if (testType.equalsIgnoreCase("UI")) {
            DriverManager.getDriver(); // Sadece UI testleri için WebDriver başlat
        }
    }

    @AfterClass(alwaysRun = true)
    @Parameters({"testType"})
    public void tearDown(@Optional("UI") String testType) {
        if (testType.equalsIgnoreCase("UI")) {
            DriverManager.quitDriver(); // Sadece UI testleri için WebDriver kapat
        }
    }
}
