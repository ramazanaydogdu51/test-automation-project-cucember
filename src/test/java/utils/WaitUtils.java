package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {
    private static final int TIMEOUT = 10;

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.visibilityOf(element));
    }
}
