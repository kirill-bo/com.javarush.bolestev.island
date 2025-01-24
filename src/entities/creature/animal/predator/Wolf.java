package entities.creature.animal.predator;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Wolf extends Predator {
    public Wolf(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Wolf(new AnimalConfig());
    }
}