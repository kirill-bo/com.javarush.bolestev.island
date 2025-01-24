package entities.creature.animal.оmnivore;

import entities.Animal;
import entities.creature.plant.Plant;
import java.util.List;

public abstract class Omnivore extends Animal {
    public Omnivore(double weight, int maxOnCell, int speed, double foodNeed, String emoji) {
        super(weight, maxOnCell, speed, foodNeed, emoji);
    }
/*
    @Override
    public void eat() {
        if (!isAlive || currentCell == null) return;

        // Сначала пытаемся съесть других животных
        List<Animal> animals = currentCell.getAnimals();
        for (Animal target : animals) {
            if (!target.isAlive() || target == this) continue;

            // Получаем вероятность поедания
            double chance = getEatingProbability(this, target);
            if (Math.random() < chance) {
                // Удаляем жертву из клетки, насыщаемся
                currentCell.removeAnimal(target);
                this.consumeFood(target.getWeight());
                target.die();
                actions.add("Съел " + target.getEmoji());
                return; // Если поели, прекращаем поиск пищи
            }
        }

        // Если животных не удалось съесть, едим растения
        List<Plant> plants = currentCell.getPlants();
        if (!plants.isEmpty()) {
            Plant plant = plants.get(0);
            double foodEaten = Math.min(plant.getWeight(), foodNeed);
            plant.reduceWeight(foodEaten);
            this.consumeFood(foodEaten);

            // Если растение полностью съедено, удаляем его
            if (plant.getWeight() <= 0) {
                currentCell.getPlants().remove(plant);
            }
            actions.add("Съел растение на " + foodEaten + " кг");
        } else {
            actions.add("Нет доступной еды");
        }
    }

    protected void consumeFood(double foodEaten) {
        foodNeed -= foodEaten;
        if (foodNeed < 0) foodNeed = 0;
    }*/
}