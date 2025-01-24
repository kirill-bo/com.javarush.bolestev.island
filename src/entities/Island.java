package entities;

import entities.creature.plant.Plant;
import entities.сonfig.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Island {
    public final int rows;
    public final int cols;
    public final Cell[][] cells;

    public Island(String configPath) {
        IslandConfig config = ConfigLoader.loadIslandConfig(configPath);
        this.rows = config.getRows();
        this.cols = config.getCols();
        this.cells = new Cell[rows][cols];
        initializeCells();
    }

    private void initializeCells() {
        Random random = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = new Cell(row, col, this);

                // Добавляем случайное количество растений (до 200)
                int plantCount = random.nextInt(201); // Случайное число от 0 до 200 растений
                for (int i = 0; i < plantCount; i++) {
                    double plantWeight = random.nextDouble(0.5, 2.0); // Вес каждого растения от 0.5 до 2.0 кг
                    Plant plant = new Plant(plantWeight);
                    cell.addPlant(plant);
                }

                // Создание животных
                for (Map.Entry<String, AnimalConfig> entry : AnimalConfigLoader.getAllAnimalConfigs().entrySet()) {
                    String type = entry.getKey();
                    AnimalConfig config = entry.getValue();

                    // Добавляем случайное количество животных от 1 до maxOnCell
                    int count = ThreadLocalRandom.current().nextInt(1, config.getMaxOnCell() + 1);
                    for (int i = 0; i < count; i++) {
                        Animal animal = AnimalFactory.createAnimal(type);
                        cell.addAnimal(animal);
                        animal.setCurrentCell(cell);
                    }
                }

                // Добавляем клетку в массив
                cells[row][col] = cell;
            }
        }

        // Отображаем таблицу с количеством животных и растений
        printIsland();
    }
    public void waitBeforeStart(int seconds) {
        System.out.println("\nОжидаем " + seconds + " секунд перед запуском симуляции...\n");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return cells[row][col];
        }
        return null; // Если координаты выходят за границы острова
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

//    public void printIsland() {
//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < cols; col++) {
//                Cell cell = cells[row][col];
//                StringBuilder cellSummary = new StringBuilder();
//
//                // Животные
//                for (AnimalConfig config : AnimalConfigLoader.getAllAnimalConfigs().values()) {
//                    String type = config.getType();
//                    if (type == null) continue;
//
//                    long count = cell.countAnimalsOfType(AnimalFactory.getAnimalClass(type));
//                    if (count > 0) {
//                        cellSummary.append(config.getEmoji()).append(count).append(" ");
//                    }
//                }
//
//                // Растения
//                if (!cell.getPlants().isEmpty()) {
//                    double totalPlantWeight = cell.getPlants().stream().mapToDouble(Plant::getWeight).sum();
//                    cellSummary.append("🌱").append(String.format("%.1f", totalPlantWeight)).append(" ");
//                }
//
//                System.out.print(cellSummary.toString().trim() + "\t");
//            }
//            System.out.println();
//        }
//    }

    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }


    public void printIsland() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = cells[row][col];
                StringBuilder cellContent = new StringBuilder();

                // Отображение животных
                cell.getAnimals().stream()
                        .collect(Collectors.groupingBy(Animal::getEmoji, Collectors.counting()))
                        .forEach((emoji, count) -> cellContent.append(emoji).append(count).append(" "));

                // Отображение растений
                if (!cell.getPlants().isEmpty()) {
                    cellContent.append("🌱").append(cell.getPlants().size()).append(" ");
                }

                System.out.print(cellContent + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}