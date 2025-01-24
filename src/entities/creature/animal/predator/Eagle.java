package entities.creature.animal.predator;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Eagle extends Predator {
    public Eagle(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Eagle(new AnimalConfig());
    }
}