package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = JsonReader.getUrl("browser");
            boolean isHeadless = Boolean.parseBoolean(JsonReader.getUrl("headless"));
            boolean useProfile = Boolean.parseBoolean(JsonReader.getUrl("useProfile"));

            log.info("Initializing WebDriver for: " + browser + ", Headless: " + isHeadless + ", Use Profile: " + useProfile);

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    if (useProfile) {
                        String profilePath = "C:\\Users\\51ram\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles"; // Profil yolu burada
                        firefoxOptions.addArguments("-profile", profilePath);
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    break;
            }
            driver.manage().window().maximize();
        }
        return driver;
    }


    public static synchronized void quitDriver() {
        if (driver != null) {
            CommonLibWeb.captureScreenshot(driver,"Captured Before Closing");
            log.info("Closing WebDriver...");
           driver.quit();
            driver = null;
        }
    }
}
