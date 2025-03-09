package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);
    private WebDriver driver;
    private String homePageUrl = "https://www.amazon.com";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        log.info("Navigating to Amazon homepage: {}", homePageUrl);
        driver.get(homePageUrl);
    }

    public boolean isHomePageDisplayed() {
        String actualUrl = driver.getCurrentUrl();
        log.info("Checking if Amazon homepage is displayed...");
        log.info("Expected URL: {}", homePageUrl);
        log.info("Actual URL: {}", actualUrl);
        Assert.assertEquals(actualUrl, homePageUrl, "Amazon homepage is not displayed!");
        log.info("Amazon homepage is verified successfully.");
        return true;
    }
}
