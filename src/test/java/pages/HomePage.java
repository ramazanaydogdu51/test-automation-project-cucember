package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.JsonReader;
import utils.UICommonLib;

import java.util.List;
import java.util.NoSuchElementException;

public class HomePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);
    private WebDriver driver;
    private By homePage;
    public By addToCart;
    public By productOnBasket;
    private String homePageUrl;
    public By deleteProduct;
    public By productList;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.homePage = By.xpath(JsonReader.getLocator("HomePage", "searchBox"));
        this.addToCart = By.name(JsonReader.getLocator("HomePage", "addToCart"));
        this.productOnBasket = By.xpath(JsonReader.getLocator("HomePage", "productOnBasket"));
        this.deleteProduct = By.xpath(JsonReader.getLocator("HomePage", "deleteProduct"));
        this.productList = By.xpath(JsonReader.getLocator("HomePage", "productList"));
        this.homePageUrl = JsonReader.getUrl("amazonUrl");

    }

    public void openHomePage() {
        log.info("Opening Amazon homepage...");
        log.info("Navigating to Amazon homepage: {}", homePageUrl);
        driver.get(homePageUrl);
    }

    public void searchProduct(String productName) {
        log.info("Searching for product: {}", productName);
//        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "searchBox"))).sendKeys(productName);
//        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "searchButton"))).click();
    }
    // 📌 Tüm ürünleri sayfadan alır
    public static List<WebElement> getAllProducts(WebDriver driver) {
        return driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
    }

    // 📌 Ürün başlığını döndürür
    public static String getProductTitle(WebElement product) {
        return product.findElement(By.tagName("h2")).getText();
    }

    // 📌 Ürünün indirimli olup olmadığını kontrol eder
    public static boolean isProductDiscounted(WebElement product) {
        List<WebElement> discountElements = product.findElements(By.xpath(".//span[contains(@class, 'a-text-price')]"));
        return !discountElements.isEmpty() && discountElements.get(0).isDisplayed();
    }

    // 📌 Ürünü sepete ekler
    public static void addProductToCart(WebElement product) {
        WebElement addToCartButton = product.findElement(By.name("submit.addToCart"));
        addToCartButton.click();
    }

//    public static WebElement getFirstNonDiscountedProduct(WebDriver driver, HomePage homePage) {
//        List<WebElement> products = getAllProducts(driver);
//
//        for (WebElement product : products) {
//            try {
//                if (!homePage.isProductDiscounted(product)) {
//                    WebElement addToButton = product.findElement(By.name("submit.addToCart"));
//                    if (addToButton.isDisplayed()) {
//                        log.info("✅ Seçilen indirimde olmayan ürün: " + homePage.getProductTitle(product));
//                        return product;
//                    }
//                }
//            } catch (NoSuchElementException e) {
//                log.warn("⚠️ Sepete ekleme butonu bulunamadı, bu ürün atlanıyor.");
//            }
//        }
//        return null;
//    }
    public static void addProductToCart(WebDriver driver, WebElement product) {
        if (product == null) {
            throw new RuntimeException("❌ Sepete eklenmesi için bir ürün seçilmedi!");
        }

        WebElement addToCartButton = product.findElement(By.name("submit.addToCart"));
        addToCartButton.click();
        log.info("🛒 Sepete eklenen ürün: " + product.findElement(By.tagName("h2")).getText());
    }

    public List<WebElement> getAllProducts() {
        return UICommonLib.getElements(driver, productList);
    }


    public WebElement getFirstNonDiscountedProduct() {
        List<WebElement> products = getAllProducts();

        for (WebElement product : products) {
            try {
                if (!isProductDiscounted(product)) {
                    WebElement addToButton = product.findElement(By.name("submit.addToCart"));
                    if (addToButton.isDisplayed()) {
                        log.info("✅ Seçilen indirimde olmayan ürün: " + getProductTitle(product));
                        return product;
                    }
                }
            } catch (NoSuchElementException e) {
                log.warn("⚠️ Sepete ekleme butonu bulunamadı, bu ürün atlanıyor.");
            }
        }
        return null; // Eğer hiçbir uygun ürün bulunamazsa null döndür
    }

    public void verifyProductInCart(WebElement lastClickedElement) {
        if (lastClickedElement == null) {
            throw new RuntimeException("❌ Doğrulamak için önce bir ürün seçmelisiniz!");
        }

        String expectedProductName = getProductTitle(lastClickedElement);
        log.info("🛒 Beklenen ürün: " + expectedProductName);

        // Sepetteki ürün görünene kadar bekle
        UICommonLib.waitForElementToBeVisible(driver, productOnBasket, 5);

        try {
            WebElement actualProductImage = driver.findElement(By.xpath("//img[@class='sc-product-image ']"));
            String actualProductName = actualProductImage.getAttribute("alt");

            log.info("🛒 Sepetteki ürün adı: " + actualProductName);

            Assert.assertEquals(actualProductName, expectedProductName, "❌ Ürün adı eşleşmiyor!");
            log.info("✅ Ürün sepete başarıyla eklendi: " + expectedProductName);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("❌ Sepet içinde ürün bulunamadı!");
        }
    }

}
