package entities.сonfig;

import java.util.Map;

public class AnimalConfig {
    public String type;
    public double weight;
    public int maxOnCell;
    public int speed;
    public double foodNeed;
    public String emoji;
    public Map<String, Integer> eatingProbabilities; // Вероятности поедания

    // Геттеры
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Map<String, Integer> getEatingProbabilities() {
        // Возвращаем пустую карту, если eatingProbabilities отсутствуют
        return eatingProbabilities != null ? eatingProbabilities : Map.of();
    }
}