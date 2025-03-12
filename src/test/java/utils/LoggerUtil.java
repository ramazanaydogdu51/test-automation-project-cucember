package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.qameta.allure.Allure;

public class LoggerUtil {
    private static final Logger log = LoggerFactory.getLogger(LoggerUtil.class);

    public static void info(String message) {
        log.info(message);  // Konsola yazdır
        Allure.step("ℹ️ INFO: " + message); // Allure raporuna ekle
    }

    public static void warn(String message) {
        log.warn(message);  // Konsola yazdır
        Allure.step("⚠️ WARNING: " + message); // Allure raporuna ekle
    }

    public static void error(String message) {
        log.error(message);  // Konsola yazdır
        Allure.step("❌ ERROR: " + message); // Allure raporuna ekle
    }
}

