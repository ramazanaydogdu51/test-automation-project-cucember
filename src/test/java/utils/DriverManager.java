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
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", "chrome");
            boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
            boolean useProfile = Boolean.parseBoolean(System.getProperty("useProfile", "false"));

            log.info("Initializing WebDriver for: " + browser + ", Headless: " + isHeadless + ", UseProfile: " + useProfile);

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) firefoxOptions.addArguments("--headless");
                    if (useProfile) {
                        firefoxOptions.addArguments("-profile", "C:\\Users\\51ram\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) chromeOptions.addArguments("--headless");
                    if (useProfile) {
                        chromeOptions.addArguments("user-data-dir=C:\\Users\\51ram\\AppData\\Local\\Google\\Chrome\\User Data");
                        chromeOptions.addArguments("profile-directory=Profile 1");
                    }
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
            }
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    public static synchronized void quitDriver() {
        if (driver.get() != null) {
            log.info("Closing WebDriver...");
            driver.get().quit();
            driver.remove();
        }
    }
}
