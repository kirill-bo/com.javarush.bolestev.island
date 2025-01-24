package entities.creature.animal.herbivore;

import entities.Animal;
import entities.creature.plant.Plant;

import java.util.List;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight, int maxOnCell, int speed, double foodNeed, String emoji) {
        super(weight, maxOnCell, speed, foodNeed, emoji);
    }
/*
    @Override
    public void eat() {
        if (!isAlive || currentCell == null) return;

        // Получаем список растений в текущей клетке
        List<Plant> plants = currentCell.getPlants();
        if (!plants.isEmpty()) {
            Plant plant = plants.get(0);
            double foodEaten = Math.min(plant.getWeight(), foodNeed);

            // Потребляем растение
            plant.reduceWeight(foodEaten);
            this.consumeFood(foodEaten);

            // Удаляем растение, если оно полностью съедено
            if (plant.getWeight() <= 0) {
                currentCell.getPlants().remove(plant);
            }
            actions.add("Съел растение на " + foodEaten + " кг");
        } else {
            actions.add("Нет доступных растений для еды");
        }
    }

    protected void consumeFood(double foodEaten) {
        foodNeed -= foodEaten;
        if (foodNeed < 0) foodNeed = 0;
    }*/
}