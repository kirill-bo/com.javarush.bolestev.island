package entities.creature.animal.оmnivore;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Duck extends Omnivore {
    public Duck(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Duck(new AnimalConfig());
    }
}
