package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonReader {
    private static final Logger log = LogManager.getLogger(JsonReader.class);
    private static JsonObject locatorsJson;
    private static JsonObject configJson;
    private static final String LOCATORS_PATH = "src/test/resources/config/locators.json";
    private static final String CONFIG_PATH = "src/test/resources/config/config.json";

    public static JsonObject readJsonFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return JsonParser.parseString(content).getAsJsonObject();
        } catch (IOException e) {
            log.error("❌ Failed to read JSON file: " + filePath, e);
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            locatorsJson = JsonParser.parseString(new String(Files.readAllBytes(Paths.get(LOCATORS_PATH)))).getAsJsonObject();
            configJson = JsonParser.parseString(new String(Files.readAllBytes(Paths.get(CONFIG_PATH)))).getAsJsonObject();
            log.info("✅ JSON files loaded successfully.");
        } catch (IOException e) {
            log.error("❌ Failed to read JSON files! Please check file paths.", e);
            throw new RuntimeException(e);
        }
    }

    public static String getLocator(String page, String element) {
        if (locatorsJson.has(page) && locatorsJson.getAsJsonObject(page).has(element)) {
            return locatorsJson.getAsJsonObject(page).get(element).getAsString();
        }
        log.warn("⚠️ Locator not found: {} -> {}", page, element);
        return ""; // Return empty string instead of throwing an error
    }

    public static String getUrl(String key) {
        if (configJson.has(key)) {
            return configJson.get(key).getAsString();
        }
        log.warn("⚠️ Config value not found: {}", key);
        return ""; // Return empty string instead of throwing an error
    }
}
