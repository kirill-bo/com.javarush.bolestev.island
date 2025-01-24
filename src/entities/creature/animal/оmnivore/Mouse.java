package entities.creature.animal.оmnivore;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Mouse extends Omnivore {
    public Mouse(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Mouse(new AnimalConfig());
    }
}
