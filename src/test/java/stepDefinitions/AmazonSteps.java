package stepDefinitions;

import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.DriverManager;
import utils.JsonReader;

public class AmazonSteps {
    private static final Logger log = LogManager.getLogger(AmazonSteps.class);
    private WebDriver driver = DriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);

    @Given("I open the Amazon website")
    public void i_open_the_amazon_website() {
        homePage.openHomePage();
    }

    @Given("I log in to Amazon")
    public void i_log_in_to_amazon() {
        log.info("Logging in to Amazon...");

        String username = JsonReader.getUrl("credentials.username");
        String password = JsonReader.getUrl("credentials.password");

        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "signInButton"))).click();
        driver.findElement(By.id("ap_email")).sendKeys(username);
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys(password);
        driver.findElement(By.id("signInSubmit")).click();

        log.info("Login process completed.");
    }
}
