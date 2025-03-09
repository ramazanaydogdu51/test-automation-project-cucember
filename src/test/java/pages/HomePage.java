package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.JsonReader;

public class HomePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        log.info("Opening Amazon homepage...");
        String homePageUrl = JsonReader.getUrl("amazonUrl");
        log.info("Navigating to Amazon homepage: {}", homePageUrl);
        driver.get(homePageUrl);
    }

    public void searchProduct(String productName) {
        log.info("Searching for product: {}", productName);
        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "searchBox"))).sendKeys(productName);
        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "searchButton"))).click();
    }
}
