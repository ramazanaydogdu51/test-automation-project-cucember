package pages;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.JsonReader;
import utils.CommonLibWeb;
import java.util.List;
import java.util.NoSuchElementException;


public class HomePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);
    private static WebDriver driver;
    private By homePage;
    public By addToCart;
    public By productOnBasket;
    private String homePageUrl;
    public By deleteProduct;
    public static By productList;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.homePage = By.xpath(JsonReader.getLocator("HomePage", "searchBox"));
        this.addToCart = By.name(JsonReader.getLocator("HomePage", "addToCart"));
        this.productOnBasket = By.xpath(JsonReader.getLocator("HomePage", "productOnBasket"));
        this.deleteProduct = By.xpath(JsonReader.getLocator("HomePage", "deleteProduct"));
        this.productList = By.xpath(JsonReader.getLocator("HomePage", "productList"));
        this.homePageUrl = JsonReader.getUrl("amazonUrl");

    }


    // 📌 Ürünü sepete ekler
    public static void addProductToCart(WebElement product) {
        WebElement addToCartButton = product.findElement(By.name("submit.addToCart"));
        addToCartButton.click();
    }

    public static WebElement getFirstNonDiscountedProduct(List<WebElement> products) {

        for (WebElement product : products) {
            if (!isProductDiscounted(product) && hasAddToCartButton( product)) {
                log.info("✅ Selected product not on sale: " + getProductTitle(product));
                Allure.step("✅ Selected product not on sale: " + getProductTitle(product));
                return product;
            }
        }
        return null;
    }
    public static String getProductTitle(WebElement product) {
        return product.findElement(By.tagName("h2")).getText();
    }


    //  Ürünün indirimli olup olmadığını kontrol eder
    public static boolean isProductDiscounted(WebElement product) {

        List<WebElement> discountElements = product.findElements(By.xpath(".//span[contains(@class, 'a-text-price')]"));
        return !discountElements.isEmpty() && discountElements.get(0).isDisplayed();
    }

    private static boolean hasAddToCartButton(WebElement product) {
        WebElement addToButton = getAddToCartButton(product);
        return addToButton != null && addToButton.isDisplayed();
    }
    private static WebElement getAddToCartButton(WebElement product) {
        try {
            return product.findElement(By.name("submit.addToCart"));
        } catch (NoSuchElementException e) {
                log.warn("⚠️ No add to cart button found, this product is skipped.");
            return null;
        }
    }


    public void verifyProductInCart(WebElement lastClickedElement) {
        String expectedProductName = getExpectedProductName(lastClickedElement);
        String actualProductName = CommonLibWeb.getElementAttribute(driver,productOnBasket,"alt",10);
        log.info("🛒 Expected product: " + expectedProductName);
        Allure.step("🛒 Expected product: " + expectedProductName);
        log.info("🛒 Product in cart: " + actualProductName);
        Allure.step("🛒 Product in cart: " + actualProductName);
        CommonLibWeb.captureScreenshot(driver,"Product added to cart");
        // Beklenen ve gerçek ürün ismini karşılaştır
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(actualProductName),
                String.format("❌ Product name does not match! Expected: %s - In cart: %s", expectedProductName, actualProductName));

        log.info("✅ Product successfully added to cart: " + expectedProductName);
        Allure.step("✅ Product successfully added to cart: " + expectedProductName);
    }

    private String getExpectedProductName(WebElement lastClickedElement) {
        if (lastClickedElement == null) {
            throw new RuntimeException("❌ To validate, you must first select a product!");
        }
        return getProductTitle(lastClickedElement).trim();
    }


}
