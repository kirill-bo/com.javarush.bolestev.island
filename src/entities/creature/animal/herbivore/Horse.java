package entities.creature.animal.herbivore;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Horse extends Herbivore {
    public Horse(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Horse(new AnimalConfig());
    }
}