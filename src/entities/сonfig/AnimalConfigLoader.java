package entities.сonfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Animal;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AnimalConfigLoader {
    private static Map<String, AnimalConfig> animalConfigs;

    public static void loadAnimalConfigs(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, AnimalConfig>>() {}.getType();
            animalConfigs = gson.fromJson(reader, type);

            if (animalConfigs == null || animalConfigs.isEmpty()) {
                throw new IllegalStateException("Конфигурации животных не найдены в файле: " + filePath);
            }

            // Устанавливаем type для каждой конфигурации, если он отсутствует
            animalConfigs.forEach((key, config) -> {
                if (config.getType() == null) {
                    config.setType(key);
                }
            });

            System.out.println("Загруженные конфигурации животных:");
            animalConfigs.forEach((key, value) -> {
                System.out.println("Тип: " + key + ", Конфигурация: " + value);
            });

            // Загружаем вероятности поедания
            loadEatingProbabilities(animalConfigs);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки конфигурации животных: " + e.getMessage(), e);
        }
    }

    public static Map<String, AnimalConfig> getAllAnimalConfigs() {
        if (animalConfigs == null) {
            throw new IllegalStateException("Конфигурации животных не загружены. Вызовите loadAnimalConfigs().");
        }
        return animalConfigs;
    }

    private static void loadEatingProbabilities(Map<String, AnimalConfig> configs) {
        Map<String, Class<? extends Animal>> classMapping = AnimalFactory.getClassMapping();

        for (AnimalConfig config : configs.values()) {
            Map<Class<? extends Animal>, Double> probabilities = new HashMap<>();

            // Проверяем, есть ли вероятности поедания у животного
            if (config.getEatingProbabilities() != null) {
                for (Map.Entry<String, Integer> entry : config.getEatingProbabilities().entrySet()) {
                    Class<? extends Animal> preyClass = classMapping.get(entry.getKey());
                    if (preyClass != null) {
                        probabilities.put(preyClass, entry.getValue() / 100.0); // Преобразуем вероятность в диапазон [0, 1]
                    }
                }
            }

            // Сохраняем вероятности в глобальную карту для всех хищников
            Animal.addEatingProbabilities(classMapping.get(config.getType()), probabilities);
        }
    }
}