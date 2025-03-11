package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = JsonReader.getUrl("browser"); // config.json'dan tarayıcı al
            log.info("Initializing WebDriver for: " + browser);

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    log.info("FirefoxDriver initialized.");
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    log.info("ChromeDriver initialized.");
                    break;
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static synchronized void quitDriver() {
        if (driver != null) {
            log.info("Closing WebDriver...");
//            driver.quit();
            driver = null;
        }
    }
}
