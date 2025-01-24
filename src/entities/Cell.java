package entities;

import entities.creature.plant.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cell {
    private final int row;
    private final int col;
    private final Island island;
    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    // Конструктор
    public Cell(int row, int col, Island island) {
        this.row = row;
        this.col = col;
        this.island = island;
    }

    // Получение строки клетки
    public int getRow() {
        return row;
    }

    // Получение столбца клетки
    public int getCol() {
        return col;
    }

    // Получение острова, к которому относится клетка
    public Island getIsland() {
        return island;
    }

    // Добавление животного в клетку
    public synchronized void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // Удаление животного из клетки
    public synchronized void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
    // Добавление растения в клетку
    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    // Удаление растения из клетки
    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public synchronized List<Animal> getAnimals() {
        return new ArrayList<>(animals); // Возвращаем копию для избежания изменений
    }

    public synchronized List<Plant> getPlants() {
        return new ArrayList<>(plants); // Возвращаем копию для избежания изменений
    }

    // Удаление всех мёртвых животных из клетки
    public void removeDeadAnimals() {
        animals.removeIf(animal -> !animal.isAlive());
    }

    // Проверка, можно ли добавить животное в клетку
    public boolean canAddAnimal(Animal animal) {
        long count = countAnimalsOfType(animal.getClass());
        return count < animal.getMaxOnCell();
    }

    // Подсчёт количества животных определённого типа
    public long countAnimalsOfType(Class<? extends Animal> type) {
        return animals.stream()
                .filter(type::isInstance) // Проверяем соответствие класса
                .count();
    }

    // Подсчёт всех животных в клетке
    public int countAnimals() {
        return animals.size();
    }

    // Максимальное количество животных в клетке (может быть задано фиксированным значением)
    public int getMaxAnimals() {
        return 100; // Пример фиксированного значения
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Добавляем животных
        if (!animals.isEmpty()) {
            animals.forEach(animal -> builder.append(animal.getEmoji()).append(" "));
        }

        // Добавляем растения
        if (!plants.isEmpty()) {
            builder.append("🌱").append(plants.size()).append(" ");
        }

        return builder.toString();
    }

    public void growPlants(double growthRate, double maxPlantWeight) {
        for (Plant plant : plants) {
            plant.addWeight(growthRate);
            if (plant.getWeight() > maxPlantWeight) {
                plant.setWeight(maxPlantWeight);
            }
        }
    }
}
