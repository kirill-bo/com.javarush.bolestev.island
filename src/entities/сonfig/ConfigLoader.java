package entities.сonfig;

import com.google.gson.Gson;

import java.io.FileReader;

public class ConfigLoader {

    public static IslandConfig loadIslandConfig(String configPath) {
        try (FileReader reader = new FileReader(configPath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, IslandConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки файла конфигурации: " + e.getMessage(), e);
        }
    }
}