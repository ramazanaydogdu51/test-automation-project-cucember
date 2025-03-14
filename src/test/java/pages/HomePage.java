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
                log.info("✅ Seçilen indirimde olmayan ürün: " + getProductTitle(product));
                Allure.step("✅ Seçilen indirimde olmayan ürün: " + getProductTitle(product));
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
            log.warn("⚠️ Sepete ekleme butonu bulunamadı, bu ürün atlanıyor.");
            return null;
        }
    }


    public void verifyProductInCart(WebElement lastClickedElement) {
        String expectedProductName = getExpectedProductName(lastClickedElement);
        String actualProductName = CommonLibWeb.getElementAttribute(driver,productOnBasket,"alt",10);
        log.info("🛒 Beklenen ürün: " + expectedProductName);
        Allure.step("🛒 Beklenen ürün: " + expectedProductName);
        log.info("🛒 Sepetteki ürün: " + actualProductName);
        Allure.step("🛒 Sepetteki ürün: " + actualProductName);

        // Beklenen ve gerçek ürün ismini karşılaştır
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(actualProductName),
                String.format("❌ Ürün adı eşleşmiyor! Beklenen: %s - Sepette: %s", expectedProductName, actualProductName));

        log.info("✅ Ürün sepete başarıyla eklendi: " + expectedProductName);
        Allure.step("✅ Ürün sepete başarıyla eklendi: " + expectedProductName);
    }

    private String getExpectedProductName(WebElement lastClickedElement) {
        if (lastClickedElement == null) {
            throw new RuntimeException("❌ Doğrulamak için önce bir ürün seçmelisiniz!");
        }
        return getProductTitle(lastClickedElement).trim();
    }


}
