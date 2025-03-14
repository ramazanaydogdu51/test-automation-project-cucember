package utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;



public class CommonLibWeb {
    private static final Logger log = LogManager.getLogger(CommonLibWeb.class);


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
            log.info("⏳ Expected element to be clicked: {}", element);

            WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            webElement.click(); // ✅ Elemente normal click
            log.info("✅ Element clicked successfully: {}", element);

            // 📸 Eğer test kanıtı isteniyorsa, SS al
            if (takeScreenshot) {
                log.info("📸 Taking screenshots after element click...");
                CommonLibWeb.captureScreenshot(driver, "Click Success => " + descriptionOfPic);
            }
        } catch (TimeoutException | NoSuchElementException e) {
            log.error("❌ Element not clickable: {} - Hata: {}", element, e.getMessage());
            CommonLibWeb.captureScreenshot(driver, "ClickElement_Error");
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

            log.info("🔽 Found dropdown value to select: " + valueToSelect);
            optionElement.click();
            log.info("✅ Dropdown value successfully selected: " + valueToSelect);
        } catch (Exception e) {
            log.error("❌ Dropdown value could not be selected: " + valueToSelect, e);
            captureScreenshot(driver, "SelectDropdownValue_Error");
        }
    }

    public static void waitForTextToAppear(WebDriver driver, By element, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            log.info("⏳ Waiting for: '{}'  text to appear in '{}' element.", expectedText, element);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(element, expectedText));
            log.info("✅ Text found: '{}' -> {}", expectedText, element);
        } catch (TimeoutException e) {
            log.error("❌ TimeoutException: Expected text '{}' did not appear in the specified element: {}", expectedText, element);
            captureScreenshot(driver, "WaitForText_Error");
            throw new TimeoutException("Error: '" + expectedText + "'  text was not loaded in element  " + element + " ", e);
        }
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


    //+++++

    public static List<WebElement> getElements(WebDriver driver, By locator ) {
        try {

            List<WebElement> elements = driver.findElements(locator);

            if (elements.isEmpty()) {
                log.warn("⚠️ Warning: '{}' no elements were found for it!", locator);
            } else {
                log.info("✅ '{}' for {} elements were found.", locator, elements.size());
            }

            return elements;
        } catch (Exception e) {
            log.error("❌ Error: '{}' error retrieving elements! Error: {}", locator, e.getMessage());
            return Collections.emptyList();
        }
    }
    public static List<WebElement> getElementsV2(WebDriver driver, String pageName, String elementName ) {
        try {
           //  JSON dosyasından locator'ı al
              String locator = JsonReader.getLocator(pageName, elementName);

            if (locator == null) {
                log.error("❌ Error: In JSON '{}' locator not found!", elementName);
                return Collections.emptyList(); // Eğer locator bulunamazsa boş liste döndür
            }

            // XPath veya CSS olup olmadığına karar ver
            By byLocator = locator.startsWith("//") || locator.startsWith("(") ? By.xpath(locator) : By.cssSelector(locator);


            List<WebElement> elements = driver.findElements(byLocator);

            if (elements.isEmpty()) {
                log.warn("⚠️ Warning: '{}' no elements were found for it!", locator);
            } else {
                log.info("✅ '{}' for {} elements were found.", locator, elements.size());
            }

            return elements;
        } catch (Exception e) {
            log.error("❌ Error: '{}' Error receiving elements! Error: {}", elementName, e.getMessage());
            return Collections.emptyList();
        }
    }
    public static WebElement getElement(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            log.info("🔍 Waiting for the element to become visible | Locator: {} | Timeout: {} seconds", locator.toString(), timeoutInSeconds);

            // Wait for the element to be visible
            CommonLibWeb.waitForElementToBeVisible(driver, locator, timeoutInSeconds);

            log.info("✅ Element is now visible | Locator: {}", locator.toString());

            WebElement element = driver.findElement(locator);
            log.info("✅ Successfully located the element | Locator: {}", locator.toString());
            return element;

        } catch (TimeoutException e) {
            log.error("⏳ Timeout Exception! The element did not become visible within the specified time | Locator: {} | Timeout: {} seconds", locator.toString(), timeoutInSeconds);
            throw new RuntimeException("⏳ Timeout! Element not found within " + timeoutInSeconds + " seconds | Locator: " + locator.toString());

        } catch (java.util.NoSuchElementException e) {
            log.error("❌ No Such Element Exception! The element could not be found in the DOM | Locator: {}", locator.toString());
            throw new RuntimeException("❌ No Such Element! Locator: " + locator.toString());

        } catch (Exception e) {
            log.error("❌ Unexpected error while locating the element | Locator: {} | Error: {}", locator.toString(), e.getMessage());
            throw new RuntimeException("❌ Unexpected error while locating the element | Locator: " + locator.toString());
        }
    }
    public static String getElementAttribute(WebDriver driver,By locator, String attribute, int timeoutInSeconds) {
        try {
            log.info("🔍 Attempting to retrieve attribute | Locator: {} | Attribute: {}", locator.toString(), attribute);

            // Find the element and get the attribute value
            WebElement element = getElement(driver,locator, timeoutInSeconds);
            String attributeValue = element.getAttribute(attribute);

            if (attributeValue == null) {
                log.warn("⚠️ Attribute value is null! | Locator: {} | Attribute: {}", locator.toString(), attribute);
                return "";
            }

            String trimmedValue = attributeValue.trim();
            log.info("✅ Successfully retrieved attribute | Locator: {} | Attribute: {} | Value: {}", locator.toString(), attribute, trimmedValue);
            return trimmedValue;

        } catch (Exception e) {
            log.error("❌ Error retrieving attribute! | Locator: {} | Attribute: {} | Error: {}", locator.toString(), attribute, e.getMessage());
            throw new RuntimeException("❌ Failed to retrieve attribute! Locator: " + locator.toString() + " | Attribute: " + attribute);
        }
    }
    public static String getElementText(WebElement element) {
        if (element == null) {
            throw new RuntimeException("❌ Element is null! Cannot retrieve text.");
        }
        return element.getText().trim();
    }

    public static void openWebsite(WebDriver driver, String url) {

        log.info("🌍 Navigating to the website: {}", url);
        Allure.step("🌍 Navigating to the website: {"+url+"}");

        String fullUrl = JsonReader.getUrl(url);
        if (fullUrl != null) {
            driver.get(fullUrl);
            captureScreenshot(driver, fullUrl);
            log.info("✅ Successfully opened the website: {}", fullUrl);
        } else {
            log.error("❌ Failed to open the website. URL not found in JSON: {}", url);
            captureScreenshot(driver, fullUrl);
            throw new RuntimeException("URL not found in JSON: " + url);
        }
    }

    public static void verifyWebsiteUrl(WebDriver driver, String url) {
        log.info("🔍 Verifying if the current website matches the expected URL...");
        Allure.step("🔍 Verifying if the current website matches the expected URL...");

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = JsonReader.getUrl(url);

        log.info("➡ Expected URL: {}", expectedUrl);
        Allure.step("➡ Expected URL: {"+expectedUrl+"}");
        log.info("➡ Actual URL: {}", actualUrl);
        Allure.step("➡ Actual URL: {"+actualUrl+"}");

        if (actualUrl.equals(expectedUrl)) {
            log.info("✅ The website URL is correct!");
            Allure.step("✅ The website URL is correct!");
            captureScreenshot(driver,actualUrl);
        } else {
            log.error("❌ URL Mismatch! Expected: {}, but found: {}", expectedUrl, actualUrl);
            Allure.step("❌ URL Mismatch! Expected: {"+expectedUrl+"}, but found: {"+actualUrl+"}" );
        }

        Assert.assertEquals(actualUrl, expectedUrl, "The website URL does not match!");
    }

    public static WebElement clickElementByJson(WebDriver driver, String pageName, String elementName) {
        log.info("Trying to click on: {} - {}", pageName, elementName);
        Allure.step("Trying to click on: {"+pageName+"} - {"+elementName+"}");

        // JSON'dan lokatörü al
        String locator = JsonReader.getLocator(pageName, elementName);
        if (locator != null) {
            By byElement = By.xpath(locator); // XPath kullanılıyor
            clickElement(driver, byElement, true, "Clicking " + pageName + " - " + elementName);
            return driver.findElement(By.xpath(locator)); // Son tıklanan elementi döndür
        } else {
            throw new RuntimeException("Element not found in JSON: " + pageName + " - " + elementName);
        }
    }



        public static void writeTextToLastClickedElement(WebElement lastClickedElement, String text) {

            if (lastClickedElement != null) {
                log.info("✍ Writing text '{}' in last clicked element", text);
                Allure.step("✍ Writing text '{"+text+"}' in last clicked element");
                lastClickedElement.clear();
                lastClickedElement.sendKeys(text);
                log.info("✅ Successfully wrote text: '{}'", text);
                Allure.step("✅ Successfully wrote text: '{"+text+"}'");

            } else {
                log.error("❌ No element was clicked before writing.");
                Allure.step("❌ No element was clicked before writing.");
                throw new RuntimeException("No element was clicked before writing.");
            }
        }



}