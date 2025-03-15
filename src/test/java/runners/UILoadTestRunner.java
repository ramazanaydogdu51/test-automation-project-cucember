package runners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UILoadTestRunner {
    private WebDriver driver;
    private static final int TAB_COUNT = 4;  // Açılacak sekme sayısı
    private static final String TEST_URL = "https://www.amazon.com.tr";  // Test edilecek URL
    private List<Long> responseTimes = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void performLoadTest() {
        long totalResponseTime = 0;

        for (int i = 0; i < TAB_COUNT; i++) {
            long responseTime = openNewTabAndMeasureResponseTime();
            responseTimes.add(responseTime);
            totalResponseTime += responseTime;
            System.out.println("✅ Tab " + (i + 1) + " Load Time: " + responseTime + " ms");
        }

        // Ortalama hesapla
        double averageResponseTime = (double) totalResponseTime / TAB_COUNT;
        System.out.println("🔥 Ortalama Response Time: " + averageResponseTime + " ms");
    }

    private long openNewTabAndMeasureResponseTime() {
        // Yeni sekme aç
        ((JavascriptExecutor) driver).executeScript("window.open('" + TEST_URL + "', '_blank');");

        // Sekmelere geçiş yap
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        // Sayfa yüklenme süresini ölç
        long start = (long) ((JavascriptExecutor) driver).executeScript("return window.performance.timing.navigationStart;");
        long end = (long) ((JavascriptExecutor) driver).executeScript("return window.performance.timing.loadEventEnd;");
        return end - start;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
