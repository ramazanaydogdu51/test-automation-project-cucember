package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
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


    @Given("I open the Amazon website")
    public void i_open_the_amazon_website() {
        homePage.openHomePage();
    }

    @Given("I open the {string} website")
    public void iOpenTheWebsite(String url) {
        UICommonLib.openWebsite(driver, url);
    }


//    @Given("I open the {string} website")
//    public void iOpenTheWebsite(String url) {
//        log.info("🌍 Navigating to the website: {}", url);
//
//        String fullUrl = JsonReader.getUrl(url);
//        if (fullUrl != null) {
//            driver.get(fullUrl);
//            UICommonLib.captureScreenshot(driver, fullUrl);
//            log.info("✅ Successfully opened the website: {}", fullUrl);
//        } else {
//            log.error("❌ Failed to open the website. URL not found in JSON: {}", url);
//            UICommonLib.captureScreenshot(driver, fullUrl);
//            throw new RuntimeException("URL not found in JSON: " + url);
//        }
//    }


//    @Then("I click {string} {string}")
//    public void iClick(String pageName, String elementName) {
//        log.info("Trying to click on: {} - {}", pageName, elementName);
//
//        // JSON'dan lokatörü al
//        String locator = JsonReader.getLocator(pageName, elementName);
//        if (locator != null) {
//            By byElement = By.xpath(locator); // XPath kullanılıyor
//            UICommonLib.clickElement(driver, byElement, true, "Clicking " + pageName + " - " + elementName);
//        } else {
//            throw new RuntimeException("Element not found in JSON: " + pageName + " - " + elementName);
//        }
//        lastClickedElement = driver.findElement(By.xpath(locator));
//    }

    @Then("I click {string} {string}")
    public void iClick(String pageName, String elementName) {
        lastClickedElement = UICommonLib.clickElementByJson(driver, pageName, elementName);
    }

    @Then("I list {string} {string}")
    public void iList(String pageName, String elementName) {
       UICommonLib.getElementsV2(driver,pageName,elementName);
    }



//    @Then("I verify {string} website is correct")
//    public void iVerifyWebsiteIsCorrect(String url) {
//        log.info("🔍 Verifying if the current website matches the expected URL...");
//
//        String actualUrl = driver.getCurrentUrl();
//        String expectedUrl = JsonReader.getUrl(url);
//
//        log.info("➡ Expected URL: {}", expectedUrl);
//        log.info("➡ Actual URL: {}", actualUrl);
//
//        if (actualUrl.equals(expectedUrl)) {
//            log.info("✅ The website URL is correct!");
//        } else {
//            log.error("❌ URL Mismatch! Expected: {}, but found: {}", expectedUrl, actualUrl);
//        }
//
//        Assert.assertEquals(actualUrl, expectedUrl, "The website URL does not match!");
//    }

    @Then("I verify {string} website is correct")
    public void iVerifyWebsiteIsCorrect(String url) {
        UICommonLib.verifyWebsiteUrl(driver, url);
    }


//    @Then("I write {string} text")
//    public void iWriteText(String text) {
//        if (lastClickedElement != null) {
//            log.info("✍ Writing text '{}' in last clicked element", text);
//            lastClickedElement.clear();
//            lastClickedElement.sendKeys(text);
//            log.info("✅ Successfully wrote text: '{}'", text);
//        } else {
//            log.error("❌ No element was clicked before writing.");
//            throw new RuntimeException("No element was clicked before writing.");
//        }
//    }

    @Then("I write {string} text")
    public void iWriteText(String text) {
        UICommonLib.writeTextToLastClickedElement(lastClickedElement, text);
    }




//    @Then("I add product without discount")
//    public void iAddProductWithoutDiscount() {
//        List<WebElement> products = homePage.getAllProducts(driver);
//
//        for (WebElement product : products) {
//            try {
//                String productTitle = homePage.getProductTitle(product);
//                log.info("İncelenen ürün: " + productTitle);
//                if (!homePage.isProductDiscounted(product)) {
//                    homePage.addProductToCart(product);
//                    log.info("✅ Sepete eklenen ürün: " + productTitle);
//                    break;
//                } else {
//                    log.info("❌ Bu ürün indirimde, atlanıyor: " + productTitle);
//                }
//            } catch (Exception e) {
//                log.warn("⚠️ Hata oluştu, ürün işlenemedi: " + e.getMessage());
//            }
//        }
//
//
//    }



    @Then("I list all products on the page")
    public void iListAllProducts() {
        List<WebElement> products = homePage.getAllProducts();
        log.info("📋 Sayfadaki toplam ürün sayısı: " + products.size());
    }





    @Then("I check for the first product that is not discounted")
    public void iCheckFirstNonDiscountedProduct() {
        lastClickedElement = homePage.getFirstNonDiscountedProduct(driver,homePage);

        if (lastClickedElement == null) {
            throw new RuntimeException("❌ Hiçbir indirimde olmayan ürün bulunamadı!");
        }
    }




    @Then("I add the selected product to the cart")
    public void iAddSelectedProductToCart() {
        if (lastClickedElement != null) {
            homePage.addProductToCart(lastClickedElement);
            log.info("🛒 Sepete eklenen ürün: " + homePage.getProductTitle(lastClickedElement));
        } else {
            throw new RuntimeException("❌ İndirimde olmayan bir ürün seçilmedi!");
        }
    }



    @Then("I verify the product is in the cart")
    public void iVerifyProductInCart() {
        homePage.verifyProductInCart(lastClickedElement);
    }


    @Then("I delete product in the cart")
    public void iDeleteProductInTheCart() {
        UICommonLib.clickElement(driver,homePage.deleteProduct,true,"Deleted Product");
    }
}






