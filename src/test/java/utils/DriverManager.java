package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            log.info("Initializing WebDriver...");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            log.info("WebDriver initialized and browser maximized.");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            log.info("Closing WebDriver and quitting browser...");
            driver.quit();
            driver = null;
            log.info("WebDriver successfully closed.");
        }
    }
}
