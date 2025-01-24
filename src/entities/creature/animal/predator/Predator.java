package entities.creature.animal.predator;


import entities.Animal;
import java.util.List;

public abstract class Predator extends Animal {
    public Predator(double weight, int maxOnCell, int speed, double foodNeed, String emoji) {
        super(weight, maxOnCell, speed, foodNeed, emoji);
    }
/*
    @Override
    public void eat() {
        if (!isAlive || currentCell == null) return;

        // Список животных в текущей клетке
        List<Animal> animals = currentCell.getAnimals();
        for (Animal target : animals) {
            if (!target.isAlive() || target == this) continue;

            // Проверяем вероятность поедания
            double chance = getEatingProbability(this, target);
            if (Math.random() < chance) {
                // Успешное поедание
                currentCell.removeAnimal(target);
                this.consumeFood(target.getWeight());
                target.die();
                actions.add("Съел " + target.getEmoji());
                return; // Если съел, завершить цикл
            }
        }

        // Если не удалось найти еду
        actions.add("Не нашёл подходящую жертву для еды");
    }

    protected void consumeFood(double foodEaten) {
        foodNeed -= foodEaten;
        if (foodNeed < 0) foodNeed = 0;
    }*/
}