package entities.сonfig;

import entities.Animal;
import entities.creature.animal.herbivore.*;
import entities.creature.animal.predator.*;
import entities.creature.animal.оmnivore.*;

import java.util.HashMap;
import java.util.Map;


public class AnimalFactory {
    // Карта соответствия строкового типа животных и их классов
    public static final Map<String, Class<? extends Animal>> animalClassMap = new HashMap<>();

    static {
        // Регистрация всех типов животных
        animalClassMap.put("Wolf", Wolf.class);           // Волк
        animalClassMap.put("Fox", Fox.class);             // Лиса
        animalClassMap.put("Boar", Boar.class);           // Кабан
        animalClassMap.put("Goat", Goat.class);           // Коза
        animalClassMap.put("Bear", Bear.class);           // Медведь
        animalClassMap.put("Eagle", Eagle.class);         // Орел
        animalClassMap.put("Rabbit", Rabbit.class);       // Кролик
        animalClassMap.put("Mouse", Mouse.class);         // Мышь
        animalClassMap.put("Duck", Duck.class);           // Утка
        animalClassMap.put("Caterpillar", Caterpillar.class); // Гусеница
        animalClassMap.put("Horse", Horse.class);         // Лошадь
        animalClassMap.put("Deer", Deer.class);           // Олень
        animalClassMap.put("Sheep", Sheep.class);         // Овца
        animalClassMap.put("Buffalo", Buffalo.class);     // Буйвол
        animalClassMap.put("Boa", Boa.class);             // Удав
    }

    // Возвращает класс животного по его строковому типу
    public static Class<? extends Animal> getAnimalClass(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Тип животного не может быть null");
        }

        Class<? extends Animal> animalClass = animalClassMap.get(type);
        if (animalClass == null) {
            throw new IllegalArgumentException("Неизвестный тип животного: " + type);
        }
        return animalClass;
    }

    // Создаёт животное на основе типа
    public static Animal createAnimal(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Тип животного не может быть null");
        }

        AnimalConfig config = AnimalConfigLoader.getAllAnimalConfigs().get(type);

        if (config == null) {
            throw new IllegalArgumentException("Неизвестное животное: " + type);
        }

        try {
            // Создаём животное через рефлексию
            Class<? extends Animal> animalClass = getAnimalClass(type);
            return animalClass.getDeclaredConstructor(AnimalConfig.class).newInstance(config);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании животного: " + type, e);
        }
    }

    // Возвращает карту соответствия типов животных их классам
    public static Map<String, Class<? extends Animal>> getClassMapping() {
        return animalClassMap;
    }
}