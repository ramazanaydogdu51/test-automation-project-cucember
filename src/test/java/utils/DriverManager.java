package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
                    ChromeOptions options = new ChromeOptions();
//                    options.addArguments("--no-sandbox");
//                    options.addArguments("--disable-dev-shm-usage");
//                    options.addArguments("--disable-blink-features=AutomationControlled");
//                    options.addArguments("--disable-popup-blocking");
//                    options.addArguments("--disable-extensions");


                    //         options.addArguments("--headless");
//                    options.addArguments("user-data-dir=C:/Users/51ram/AppData/Local/Google/Chrome/User Data");
//                    options.addArguments("profile-directory=Default"); // Varsayılan profil dizini
//                    options.addArguments("--remote-debugging-port=0");
//                    driver = new ChromeDriver(options);
                    log.info("ChromeDriver initialized with user data.");
                    break;
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static synchronized void quitDriver() {
        if (driver != null) {
            log.info("Closing WebDriver...");
            driver.quit();
            driver = null;
        }
    }
}
