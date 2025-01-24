package entities;

import entities.creature.animal.herbivore.Herbivore;
import entities.creature.animal.predator.Predator;
import entities.creature.animal.оmnivore.Omnivore;
import entities.creature.plant.Plant;
import entities.сonfig.AnimalFactory;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    // Глобальная таблица вероятностей поедания
    protected static final Map<Class<? extends Animal>, Map<Class<? extends Animal>, Double>> eatingProbabilities = new HashMap<>();

    // Атрибуты животного
    public final double weight;
    public final int maxOnCell;
    public final int speed;
    public double foodNeed;
    public final String emoji;
    public boolean isAlive = true; // Жив ли организм

    public Cell currentCell; // Текущая клетка, в которой находится животное

    public final List<String> actions; // Лог действий животного

    public Animal(double weight, int maxOnCell, int speed, double foodNeed, String emoji) {
        this.weight = weight;
        this.maxOnCell = maxOnCell;
        this.speed = speed;
        this.foodNeed = foodNeed;
        this.emoji = emoji;
        this.actions = new java.util.ArrayList<>();
    }

    // Геттеры и сеттеры
    public double getWeight() {
        return weight;
    }

    public int getMaxOnCell() {
        return maxOnCell;
    }

    public int getSpeed() {
        return speed;
    }

    public double getFoodNeed() {
        return foodNeed;
    }

    public String getEmoji() {
        return emoji;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<String> getActions() {
        return actions;
    }

    public void clearActions() {
        actions.clear();
    }

    // Основные методы поведения
    public void eat() {
        if (!isAlive || currentCell == null) return;

        // 1. Поедание животных (для хищников и всеядных)
        if (this instanceof Predator || this instanceof Omnivore) {
            List<Animal> potentialPrey = new ArrayList<>(currentCell.getAnimals());
            for (Animal target : potentialPrey) {
                if (!target.isAlive() || target == this) continue;

                // Проверка вероятности поедания
                double chance = Animal.getEatingProbability(this.getClass(), target.getClass());
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    currentCell.removeAnimal(target);      // Удаляем жертву из клетки
                    consumeFood(target.getWeight());      // Увеличиваем сытость
                    target.die();                         // Убиваем жертву
                    System.out.println(getEmoji() + " съел " + target.getEmoji() +
                            " в клетке [" + currentCell.getRow() + "][" + currentCell.getCol() + "]");
                    return; // После успешного поедания выходим из метода
                }
            }
        }

        // 2. Поедание растений (для травоядных и всеядных)
        if (this instanceof Herbivore || this instanceof Omnivore) {
            List<Plant> plants = new ArrayList<>(currentCell.getPlants());
            if (!plants.isEmpty()) {
                Plant plant = plants.get(0); // Берём первое растение
                double foodEaten = Math.min(plant.getWeight(), foodNeed); // Сколько животное может съесть
                plant.reduceWeight(foodEaten);  // Уменьшаем вес растения
                consumeFood(foodEaten);         // Увеличиваем сытость животного

                if (plant.getWeight() <= 0) {
                    currentCell.removePlant(plant); // Удаляем растение, если оно полностью съедено
                }

                System.out.println(getEmoji() + " съел 🌱\uD83E\uDD66 на " + foodEaten + " кг в клетке [" +
                        currentCell.getRow() + "][" + currentCell.getCol() + "]");
            }
        }
    }

    public void reproduce() {
        if (!isAlive || currentCell == null) return;

        // Проверяем, есть ли особи того же типа
        long sameTypeCount = currentCell.countAnimalsOfType(this.getClass());
        if (sameTypeCount < 2) return; // Недостаточно особей для размножения

        // Проверяем, есть ли место для нового животного
        if (sameTypeCount >= 2 && currentCell.countAnimals() < currentCell.getMaxAnimals()) {
            Animal newAnimal = AnimalFactory.createAnimal(getClass().getSimpleName());
            currentCell.addAnimal(newAnimal);
            newAnimal.setCurrentCell(currentCell);

            System.out.println(getEmoji() + " размножился в клетке [" +
                    currentCell.getRow() + "][" + currentCell.getCol() + "]");
        }}

    public boolean move() {
        if (!isAlive || currentCell == null || speed == 0) return false;

        // Запоминаем начальную клетку
        int originalRow = currentCell.getRow();
        int originalCol = currentCell.getCol();

        for (int attempt = 0; attempt < 10; attempt++) { // Максимум 10 попыток перемещения
            int deltaRow = ThreadLocalRandom.current().nextInt(-speed, speed + 1);
            int deltaCol = ThreadLocalRandom.current().nextInt(-speed, speed + 1);

            // Пропускаем попытку остаться на месте
            if (deltaRow == 0 && deltaCol == 0) continue;

            int newRow = originalRow + deltaRow;
            int newCol = originalCol + deltaCol;

            // Проверяем, находится ли новая клетка в пределах границ
            if (!currentCell.getIsland().isWithinBounds(newRow, newCol)) continue;

            Cell newCell = currentCell.getIsland().getCell(newRow, newCol);

            // Проверяем, что новая клетка может вместить животное
            if (newCell != null && newCell.canAddAnimal(this)) {
                // Успешное перемещение
                currentCell.removeAnimal(this); // Убираем из текущей клетки
                newCell.addAnimal(this);       // Добавляем в новую клетку
                this.setCurrentCell(newCell);  // Обновляем текущую клетку

                System.out.println(getEmoji() + " переместился из клетки [" +
                        originalRow + "][" + originalCol + "] на клетку [" +
                        newRow + "][" + newCol + "]");
                return true;
            }
        }

        // Сообщение о неудаче
        System.out.println(getEmoji() + " остался в клетке [" +
                originalRow + "][" + originalCol + "] и не смог переместиться.");
        return false; // Все попытки перемещения не удались
    }

    public void die() {
        isAlive = false;
        actions.add("Умер");
    }

    public static void addEatingProbabilities(Class<? extends Animal> predator, Map<Class<? extends Animal>, Double> probabilities) {
        eatingProbabilities.put(predator, probabilities);
    }

    public static double getEatingProbability(Class<? extends Animal> predator, Class<? extends Animal> prey) {
        return eatingProbabilities.getOrDefault(predator, Map.of()).getOrDefault(prey, 0.0);
    }
    public void consumeFood(double foodEaten) {
        foodNeed -= foodEaten;
        if (foodNeed <= 0) {
            foodNeed = 0;
        }
    }
    public void performActions() {
        if (!isAlive || currentCell == null) return;

        boolean moved = move(); // Попытка перемещения

        if (moved) {
            eat();       // Питание доступно только после успешного перемещения
            reproduce(); // Размножение доступно только после успешного перемещения
        } else {
            System.out.println(getEmoji() + " остался в клетке [" +
                    currentCell.getRow() + "][" + currentCell.getCol() + "] и пропустил действия.");
        }
    }

    public abstract Animal createNewInstance();
}