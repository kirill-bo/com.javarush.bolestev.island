package entities.creature.animal.predator;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Fox extends Predator {
    public Fox(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Fox(new AnimalConfig());
    }
}
