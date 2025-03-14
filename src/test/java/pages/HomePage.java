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

    public void openHomePage() {
        log.info("Opening Amazon homepage...");
        log.info("Navigating to Amazon homepage: {}", homePageUrl);
        driver.get(homePageUrl);
    }

//    public void searchProduct(String productName) {
//        log.info("Searching for product: {}", productName);
////        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "searchBox"))).sendKeys(productName);
////        driver.findElement(By.xpath(JsonReader.getLocator("HomePage", "searchButton"))).click();
//    }
    // 📌 Tüm ürünleri sayfadan alır
//    public static List<WebElement> getAllProducts(WebDriver driver) {
//        return driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
//    }

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

    public static WebElement getFirstNonDiscountedProduct(WebDriver driver, HomePage homePage) {
        List<WebElement> products = getAllProducts();

        for (WebElement product : products) {
            try {
                if (!homePage.isProductDiscounted(product)) {
                    WebElement addToButton = product.findElement(By.name("submit.addToCart"));
                    if (addToButton.isDisplayed()) {
                        log.info("✅ Seçilen indirimde olmayan ürün: " + homePage.getProductTitle(product));
                        return product;
                    }
                }
            } catch (NoSuchElementException e) {
                log.warn("⚠️ Sepete ekleme butonu bulunamadı, bu ürün atlanıyor.");
            }
        }
        return null;
    }
    public static void addProductToCart(WebDriver driver, WebElement product) {
        if (product == null) {
            throw new RuntimeException("❌ Sepete eklenmesi için bir ürün seçilmedi!");
        }

        WebElement addToCartButton = product.findElement(By.name("submit.addToCart"));
        addToCartButton.click();
        log.info("🛒 Sepete eklenen ürün: " + product.findElement(By.tagName("h2")).getText());
    }

    public static List<WebElement> getAllProducts() {
        return UICommonLib.getElements(driver, productList);
    }


//    public WebElement getFirstNonDiscountedProduct() {
//        List<WebElement> products = getAllProducts();
//
//        for (WebElement product : products) {
//            try {
//                if (!isProductDiscounted(product)) {
//                    WebElement addToButton = product.findElement(By.name("submit.addToCart"));
//                    if (addToButton.isDisplayed()) {
//                        log.info("✅ Seçilen indirimde olmayan ürün: " + getProductTitle(product));
//                        return product;
//                    }
//                }
//            } catch (NoSuchElementException e) {
//                log.warn("⚠️ Sepete ekleme butonu bulunamadı, bu ürün atlanıyor.");
//            }
//        }
//        return null; // Eğer hiçbir uygun ürün bulunamazsa null döndür
//    }

//    public void verifyProductInCart(WebElement lastClickedElement) {
//        if (lastClickedElement == null) {
//            throw new RuntimeException("❌ Doğrulamak için önce bir ürün seçmelisiniz!");
//        }
//
//        String expectedProductName = getProductTitle(lastClickedElement);
//        log.info("🛒 Beklenen ürün: " + expectedProductName);
//
//        // Sepetteki ürün görünene kadar bekle
//        UICommonLib.waitForElementToBeVisible(driver, productOnBasket, 5);
//
//        try {
//            WebElement actualProductImage = driver.findElement(By.xpath("//img[@class='sc-product-image ']"));
//            String actualProductName = actualProductImage.getAttribute("alt");
//
//            log.info("🛒 Sepetteki ürün adı: " + actualProductName);
//
//            Assert.assertEquals(actualProductName, expectedProductName, "❌ Ürün adı eşleşmiyor!");
//            log.info("✅ Ürün sepete başarıyla eklendi: " + expectedProductName);
//        } catch (NoSuchElementException e) {
//            throw new RuntimeException("❌ Sepet içinde ürün bulunamadı!");
//        }
//    }

//    public void verifyProductInCart(WebElement lastClickedElement) {
//        if (lastClickedElement == null) {
//            throw new RuntimeException("❌ Doğrulamak için önce bir ürün seçmelisiniz!");
//        }
//
//        String expectedProductName = getProductTitle(lastClickedElement);
//        log.info("🛒 Beklenen ürün: " + expectedProductName);
//
//
//
//        try {
//            // Sepetteki ürünün görünmesini bekle
//            UICommonLib.waitForElementToBeVisible(driver, productOnBasket, 5);
//
//            WebElement actualProductImage = driver.findElement(productOnBasket);
//            String actualProductName = actualProductImage.getAttribute("alt");
//
//            log.info("🛒 Sepetteki ürün adı: " + actualProductName);
//
//            // Beklenen ve gerçek ürün ismini karşılaştır
//            Assert.assertEquals(actualProductName, expectedProductName, "❌ Ürün adı eşleşmiyor!");
//            log.info("✅ Ürün sepete başarıyla eklendi: " + expectedProductName);
//        } catch (TimeoutException | NoSuchElementException e) {
//            log.error("❌ Sepet içinde ürün bulunamadı! Hata: " + e.getMessage());
//            throw new RuntimeException("❌ Sepet içinde ürün bulunamadı!");
//        }
//    }




    public void verifyProductInCart(WebElement lastClickedElement) {
        String expectedProductName = getExpectedProductName(lastClickedElement);
        String actualProductName = UICommonLib.getElementAttribute(driver,productOnBasket,"alt",10);
        log.info("🛒 Beklenen ürün: " + expectedProductName);
        log.info("🛒 Sepetteki ürün: " + actualProductName);

        // Beklenen ve gerçek ürün ismini karşılaştır
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(actualProductName),
                String.format("❌ Ürün adı eşleşmiyor! Beklenen: %s - Sepette: %s", expectedProductName, actualProductName));

        log.info("✅ Ürün sepete başarıyla eklendi: " + expectedProductName);
    }
//    private String getActualProductNameFromCart() {
//        // Sepetteki ürün elementini JSON'dan al
//       // By productImageLocator = By.xpath(JsonReader.getLocator("HomePage", "productOnBasket"));
//
//        try {
//            // Sepetteki ürünün görünmesini bekle
//            UICommonLib.waitForElementToBeVisible(driver, productOnBasket, 5);
//            WebElement actualProductImage = driver.findElement(productOnBasket);
//            String actualProductName = actualProductImage.getAttribute("alt");
//            return actualProductImage.getAttribute("alt").trim();
//        } catch (TimeoutException | NoSuchElementException e) {
//            log.error("❌ Sepet içinde ürün bulunamadı! Hata: " + e.getMessage());
//            throw new RuntimeException("❌ Sepet içinde ürün bulunamadı!");
//        }
//    }

//    private WebElement getCartProductElement(WebDriver driver,By locater) {
//
//        try {
//            // Sepetteki ürünün görünmesini bekle
//            UICommonLib.waitForElementToBeVisible(driver, locater, 5);
//            return driver.findElement(locater);
//        } catch (TimeoutException | NoSuchElementException e) {
//            log.error("❌ Sepet içinde ürün bulunamadı! Hata: " + e.getMessage());
//            throw new RuntimeException("❌ Sepet içinde ürün bulunamadı!");
//        }
//    }

//    private WebElement getElement(By locator, int timeoutInSeconds) {
//        try {
//            // Elementin görünmesini bekle
//            UICommonLib.waitForElementToBeVisible(driver, locator, timeoutInSeconds);
//            return driver.findElement(locator);
//        } catch (TimeoutException | NoSuchElementException e) {
//            log.error("❌ Element bulunamadı! Locator: " + locator.toString() + " | Hata: " + e.getMessage());
//            throw new RuntimeException("❌ Element bulunamadı! Locator: " + locator.toString());
//        }
//    }
//    private WebElement getElement(By locator, int timeoutInSeconds) {
//        try {
//            log.info("🔍 Waiting for the element to become visible | Locator: {} | Timeout: {} seconds", locator.toString(), timeoutInSeconds);
//
//            // Wait for the element to be visible
//            UICommonLib.waitForElementToBeVisible(driver, locator, timeoutInSeconds);
//
//            log.info("✅ Element is now visible | Locator: {}", locator.toString());
//
//            WebElement element = driver.findElement(locator);
//            log.info("✅ Successfully located the element | Locator: {}", locator.toString());
//            return element;
//
//        } catch (TimeoutException e) {
//            log.error("⏳ Timeout Exception! The element did not become visible within the specified time | Locator: {} | Timeout: {} seconds", locator.toString(), timeoutInSeconds);
//            throw new RuntimeException("⏳ Timeout! Element not found within " + timeoutInSeconds + " seconds | Locator: " + locator.toString());
//
//        } catch (NoSuchElementException e) {
//            log.error("❌ No Such Element Exception! The element could not be found in the DOM | Locator: {}", locator.toString());
//            throw new RuntimeException("❌ No Such Element! Locator: " + locator.toString());
//
//        } catch (Exception e) {
//            log.error("❌ Unexpected error while locating the element | Locator: {} | Error: {}", locator.toString(), e.getMessage());
//            throw new RuntimeException("❌ Unexpected error while locating the element | Locator: " + locator.toString());
//        }
//    }

    //    private String getElementAttribute(By locator, String attribute, int timeoutInSeconds) {
//        WebElement element = getElement(locator, timeoutInSeconds);
//        return element.getAttribute(attribute).trim();
//    }
//    private String getElementAttribute(By locator, String attribute, int timeoutInSeconds) {
//        try {
//            log.info("🔍 Attempting to retrieve attribute | Locator: {} | Attribute: {}", locator.toString(), attribute);
//
//            // Find the element and get the attribute value
//            WebElement element = getElement(locator, timeoutInSeconds);
//            String attributeValue = element.getAttribute(attribute);
//
//            if (attributeValue == null) {
//                log.warn("⚠️ Attribute value is null! | Locator: {} | Attribute: {}", locator.toString(), attribute);
//                return "";
//            }
//
//            String trimmedValue = attributeValue.trim();
//            log.info("✅ Successfully retrieved attribute | Locator: {} | Attribute: {} | Value: {}", locator.toString(), attribute, trimmedValue);
//            return trimmedValue;
//
//        } catch (Exception e) {
//            log.error("❌ Error retrieving attribute! | Locator: {} | Attribute: {} | Error: {}", locator.toString(), attribute, e.getMessage());
//            throw new RuntimeException("❌ Failed to retrieve attribute! Locator: " + locator.toString() + " | Attribute: " + attribute);
//        }
//    }



//    private String getAttributeValueFromElement(By locater, String attribute) {
//        WebElement attributeValue  = getCartProductElement(driver,locater);
//        return attributeValue .getAttribute(attribute).trim();
//    }

    private String getExpectedProductName(WebElement lastClickedElement) {
        if (lastClickedElement == null) {
            throw new RuntimeException("❌ Doğrulamak için önce bir ürün seçmelisiniz!");
        }
        return getProductTitle(lastClickedElement).trim();
    }



}
