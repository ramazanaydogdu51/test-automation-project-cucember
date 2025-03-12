package utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Set;



public class UICommonLib {
    private static final Logger log = LogManager.getLogger(UICommonLib.class);


    // Elementi sayfanın en üstüne hizalar (start)
    public static void scrollToElementStart(WebDriver driver, By element) {
        scrollToElement(driver, element, "start");
    }

    // Elementi sayfanın ortasına hizalar (center)
    public static void scrollToElementCenter(WebDriver driver, By element) {
        scrollToElement(driver, element, "center");
    }

    // Elementi sayfanın en altına hizalar (end)
    public static void scrollToElementEnd(WebDriver driver, By element) {
        scrollToElement(driver, element, "end");
    }

    // Eğer element zaten görünüyorsa, kaydırma yapmaz (nearest)
    public static void scrollToElementNearest(WebDriver driver, By element) {
        scrollToElement(driver, element, "nearest");
    }

    // Ortak kaydırma metodu (bu sayede kod tekrarını engelleriz)
    private static void scrollToElement(WebDriver driver, By element, String position) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement webElement = driver.findElement(element);
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: '" + position + "'});", webElement);

        // Bekleme ekleyelim ki scroll tamamlanmadan SS alınmasın
        waitForElementToBeVisible(driver, element,5);
    }

    // Belirtilen elementi görünene kadar bekleyen metot
    public static void waitForElementToBeVisible(WebDriver driver, By element , int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)); // x saniye bekler
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static void clickElement(WebDriver driver, By element, boolean takeScreenshot, String descriptionOfPic) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            log.info("⏳ Tıklanacak element bekleniyor: {}", element);

            WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            webElement.click(); // ✅ Elemente normal click
            log.info("✅ Element başarıyla tıklandı: {}", element);

            // 📸 Eğer test kanıtı isteniyorsa, SS al
            if (takeScreenshot) {
                log.info("📸 Element tıklama sonrası ekran görüntüsü alınıyor...");
                UICommonLib.captureScreenshot(driver, "Click Success => " + descriptionOfPic);
            }
        } catch (TimeoutException | NoSuchElementException e) {
            log.error("❌ Element tıklanamadı: {} - Hata: {}", element, e.getMessage());
            UICommonLib.captureScreenshot(driver, "ClickElement_Error");
            throw e;
        }
    }

    public static void selectDropdownValue(WebDriver driver, String pageName, String dropdownKey, String valueToSelect) {
        try {
            By dropdown = By.cssSelector(JsonReader.getLocator(pageName, dropdownKey));
            clickElement(driver, dropdown, true, dropdownKey);

            By option = By.xpath("//li[contains(text(),'" + valueToSelect + "')]");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(option));

            log.info("🔽 Seçilecek dropdown değeri bulundu: " + valueToSelect);
            optionElement.click();
            log.info("✅ Dropdown değeri başarıyla seçildi: " + valueToSelect);
        } catch (Exception e) {
            log.error("❌ Dropdown değeri seçilemedi: " + valueToSelect, e);
            captureScreenshot(driver, "SelectDropdownValue_Error");
        }
    }

    public static void waitForTextToAppear(WebDriver driver, By element, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            log.info("⏳ Bekleniyor: '{}' metni '{}' elementinde görünsün.", expectedText, element);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(element, expectedText));
            log.info("✅ Metin bulundu: '{}' -> {}", expectedText, element);
        } catch (TimeoutException e) {
            log.error("❌ TimeoutException: Beklenen metin '{}' belirtilen elementte görünmedi: {}", expectedText, element);
            captureScreenshot(driver, "WaitForText_Error");
            throw new TimeoutException("HATA: '" + expectedText + "' metni, element " + element + " içinde yüklenmedi.", e);
        }
    }

    public static void switchToNewTab2(WebDriver driver) {
        String mainWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        log.info("🔄 Açık sekmeler: {}", windowHandles);
        for (String window : windowHandles) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                log.info("✅ Yeni sekmeye geçildi: {}", driver.getTitle());
                captureScreenshot(driver, driver.getTitle());
                return;
            }
        }
        log.error("❌ Yeni sekme bulunamadı!");
        captureScreenshot(driver, "SwitchToNewTab_Error");
    }

    public static void switchToNewTab(WebDriver driver, boolean takeScreenshot) {
        String mainWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();

        log.info("🔄 Açık sekmeler: {}", windowHandles);

        for (String window : windowHandles) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                log.info("✅ Yeni sekmeye geçildi: {}", driver.getTitle());

                if (takeScreenshot) {
                    captureScreenshot(driver, "SwitchedTo_" + driver.getTitle());
                }
                return;
            }
        }

        log.error("❌ Yeni sekme bulunamadı!");
        throw new RuntimeException("Yeni sekmeye geçiş başarısız oldu!");
    }


    public static void verifyTextInElements(WebDriver driver, By locator, String expectedText, SoftAssert softAssert) {
        List<WebElement> elements = driver.findElements(locator);

        if (elements.isEmpty()) {
            log.warn("⚠️ Uyarı: Verilen locator için hiçbir element bulunamadı: " + locator.toString());
        }

        for (WebElement element : elements) {
            String actualText = element.getText().trim();
            log.info("🔍 Kontrol edilen element metni: '" + actualText + "' (Beklenen: '" + expectedText + "')");

            boolean containsExpectedText = actualText.contains(expectedText);

            if (!containsExpectedText) {
                log.error("❌ HATA: Beklenen metin bulunamadı!");
                log.error("   📌 Beklenen: '" + expectedText + "'");
                log.error("   📌 Gelen: '" + actualText + "'");
                captureScreenshot(driver, "TextVerificationFailed_" + expectedText);
            }

            softAssert.assertTrue(containsExpectedText,
                    "❌ Beklenen içerik bulunamadı! Beklenen: '" + expectedText + "' - Gelen: '" + actualText + "'");
        }
    }

    public static boolean isElementDisplayed(WebDriver driver, By elementLocator, String elementName) {
        try {
            log.info("🔍 Kontrol ediliyor: " + elementName + " (Locator: " + elementLocator.toString() + ")");

            WebElement element = driver.findElement(elementLocator);

            // Sayfanın ortasına kaydır
            scrollToElementStart(driver, elementLocator);

            // SS al ve log yazdır
            captureScreenshot(driver, elementName + "_Displayed");
            log.info("✅ ELEMENT GÖRÜNTÜLENDİ: " + elementName + " (Locator: " + elementLocator.toString() + ")");

            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            // Eğer element bulunamazsa yine SS al ve hata mesajı bas
            log.error("❌ HATA: " + elementName + " elementi bulunamadı! (Locator: " + elementLocator.toString() + ")");
            log.error("📌 Sayfa başlığı: " + driver.getTitle());
            captureScreenshot(driver, elementName + "_Not_Found");
            return false;
        } catch (Exception e) {
            // Beklenmeyen bir hata alındığında daha fazla detay ekleyelim
            log.error("⚠️ BEKLENMEYEN HATA: " + elementName + " görüntülenirken hata oluştu!", e);
            log.error("📌 Sayfa başlığı: " + driver.getTitle());
            captureScreenshot(driver, elementName + "_UnexpectedError");
            return false;
        }
    }

    public static boolean isElementNotDisplayed(WebDriver driver, By elementLocator, String elementName) {
        try {
            log.info("🔍 Kontrol ediliyor: " + elementName + " (Locator: " + elementLocator.toString() + ")");

            List<WebElement> elements = driver.findElements(elementLocator);

            if (elements.isEmpty()) {
                log.info("✅ ELEMENT YOK: " + elementName + " sayfada bulunamadı.");
                return true;
            } else {
                log.warn("⚠️ ELEMENT BULUNDU: " + elementName + " beklenmedik şekilde göründü!");
                captureScreenshot(driver, elementName + "_Unexpectedly_Found");
                return false;
            }
        } catch (Exception e) {
            log.error("⚠️ BEKLENMEYEN HATA: " + elementName + " kontrol edilirken hata oluştu!", e);
            captureScreenshot(driver, elementName + "_UnexpectedError");
            return false;
        }
    }

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        Allure.addAttachment(screenshotName, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    public static void scrollDown(WebDriver driver, int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixels + ");");
    }


}