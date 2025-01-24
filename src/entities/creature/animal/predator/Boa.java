package entities.creature.animal.predator;

import entities.Animal;
import entities.сonfig.AnimalConfig;

public class Boa extends Predator{
    public Boa(AnimalConfig config) {
        super(config.getWeight(), config.getMaxOnCell(), config.getSpeed(), config.getFoodNeed(), config.getEmoji());
    }

    @Override
    public Animal createNewInstance() {
        return new Boa(new AnimalConfig());
    }
}