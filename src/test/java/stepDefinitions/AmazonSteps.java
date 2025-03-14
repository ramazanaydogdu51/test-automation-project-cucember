package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import utils.*;

import java.util.List;


public class AmazonSteps {
    private static final Logger log = LogManager.getLogger(AmazonSteps.class);
    private WebDriver driver = DriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);
    private WebElement lastClickedElement;

    public static List<WebElement> listedElements;


    @Given("I open the {string} website")
    public void iOpenTheWebsite(String url) {
        CommonLibWeb.openWebsite(driver, url);
    }

    @Then("I click on the {string} element on {string} page")
    public void iClick(String pageName, String elementName) {
        lastClickedElement = CommonLibWeb.clickElementByJson(driver, pageName, elementName);
    }

    @Then("I list the {string} on the {string} page")
    public void iList(String pageName, String elementName) {
        listedElements = CommonLibWeb.getElementsV2(driver, pageName, elementName);
        log.info("✅ {} adet element bulundu.", listedElements.size());
        Allure.step("✅ {"+listedElements.size()+"} adet element bulundu." );
    }

    @Then("I verify {string} website is correct")
    public void iVerifyWebsiteIsCorrect(String url) {
        CommonLibWeb.verifyWebsiteUrl(driver, url);
    }

    @Then("I write {string} text")
    public void iWriteText(String text) {
        CommonLibWeb.writeTextToLastClickedElement(lastClickedElement, text);
        CommonLibWeb.captureScreenshot(driver,text);
    }

    @Then("I check for the first product that is not discounted")
    public void iCheckFirstNonDiscountedProduct() {
        lastClickedElement = homePage.getFirstNonDiscountedProduct(listedElements);
    }


    @Then("I add the selected product to the cart")
    public void iAddSelectedProductToCart() {

            homePage.addProductToCart(lastClickedElement);
            log.info("🛒 Sepete eklenen ürün: " + homePage.getProductTitle(lastClickedElement));
        Allure.step("🛒 Sepete eklenen ürün: " + homePage.getProductTitle(lastClickedElement));

    }


    @Then("I verify the product is in the cart")
    public void iVerifyProductInCart() {
        homePage.verifyProductInCart(lastClickedElement);
    }


    @Then("I delete product in the cart")
    public void iDeleteProductInTheCart() {
        CommonLibWeb.clickElement(driver, homePage.deleteProduct, true, "Deleted Product");
    }


}






