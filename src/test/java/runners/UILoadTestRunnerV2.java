package runners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UILoadTestRunnerV2 {
    private WebDriver driver;
    private static final int TAB_COUNT = 10;  // Açılacak sekme sayısı
    private static final String TEST_URL = "https://www.amazon.com";  // Test edilecek URL
    private List<Long> responseTimes = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void performParallelLoadTest() {
        ExecutorService executor = Executors.newFixedThreadPool(TAB_COUNT);
        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 0; i < TAB_COUNT; i++) {
            futures.add(executor.submit(this::openNewTabAndMeasureResponseTime));
        }

        executor.shutdown();

        long totalResponseTime = 0;
        int completedTasks = 0;

        for (Future<Long> future : futures) {
            try {
                long responseTime = future.get();  // Paralel çalışan thread'lerden sonuçları al
                responseTimes.add(responseTime);
                totalResponseTime += responseTime;
                completedTasks++;
                System.out.println("✅ Tab " + completedTasks + " Load Time: " + responseTime + " ms");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Ortalama hesapla
        double averageResponseTime = (double) totalResponseTime / TAB_COUNT;
        System.out.println("🔥 Ortalama Response Time: " + averageResponseTime + " ms");
    }

    private long openNewTabAndMeasureResponseTime() {
        synchronized (this) {
            ((JavascriptExecutor) driver).executeScript("window.open('" + TEST_URL + "', '_blank');");
        }

        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        // Sayfa yüklenme süresini ölç
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long start = (long) js.executeScript("return window.performance.timing.navigationStart;");
        long end = (long) js.executeScript("return window.performance.timing.loadEventEnd;");
        return end - start;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
