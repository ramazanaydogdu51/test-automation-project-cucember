package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.DriverManager;

public class AmazonSteps {
    private static final Logger log = LogManager.getLogger(AmazonSteps.class);
    private WebDriver driver = DriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);

    @Given("I open the Amazon website")
    public void i_open_the_amazon_website() {
        log.info("Opening Amazon homepage...");
        homePage.openHomePage();
    }

    @Then("I should see the Amazon homepage")
    public void i_should_see_the_amazon_homepage() {
        log.info("Verifying Amazon homepage...");
        homePage.isHomePageDisplayed();
        log.info("Amazon homepage is displayed successfully.");
    }
}
