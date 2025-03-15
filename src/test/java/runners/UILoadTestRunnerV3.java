package runners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.JsonReader;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class UILoadTestRunnerV3 {
    private WebDriver driver;
    private List<Long> responseTimes = new ArrayList<>();
    private List<Integer> jsonValues;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // JSON'dan int değerlerini oku
        jsonValues = JsonReader.getIntValues();
    }

    @Test
    public void performParallelLoadTest() {
        ExecutorService executor = Executors.newFixedThreadPool(jsonValues.size());
        List<Future<Long>> futures = new ArrayList<>();

        for (Integer value : jsonValues) {
            futures.add(executor.submit(() -> openNewTabAndMeasureResponseTime(value)));
        }

        executor.shutdown();

        long totalResponseTime = 0;
        int completedTasks = 0;

        for (Future<Long> future : futures) {
            try {
                long responseTime = future.get();
                responseTimes.add(responseTime);
                totalResponseTime += responseTime;
                completedTasks++;
                System.out.println("✅ Tab " + completedTasks + " (" + jsonValues.get(completedTasks - 1) + ") Load Time: " + responseTime + " ms");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Ortalama hesapla
        double averageResponseTime = (double) totalResponseTime / jsonValues.size();
        System.out.println("🔥 Ortalama Response Time: " + averageResponseTime + " ms");
    }

    private long openNewTabAndMeasureResponseTime(int value) {
        String url = "https://reqres.in/api/users/" + value;

        synchronized (this) {
            ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
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
           // driver.quit();
        }
    }
}
