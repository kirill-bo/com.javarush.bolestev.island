package entities;

import entities.сonfig.AnimalConfigLoader;

public class App {
    public static void main(String[] args) {
        // Путь к файлам конфигурации
        String islandConfigPath = "src/entities/сonfig/islandConfig.json";
        String animalsConfigPath = "src/entities/сonfig/animals.json";

        // Загружаем конфигурации животных
        AnimalConfigLoader.loadAnimalConfigs(animalsConfigPath);

        // Создаём симулятор
        Simulator simulator = new Simulator(islandConfigPath);

        // Запускаем симуляцию
        simulator.start();
    }
}