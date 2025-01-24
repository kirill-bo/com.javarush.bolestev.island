package entities;

import entities.сonfig.ConfigLoader;
import entities.сonfig.IslandConfig;
import entities.сonfig.ProcessIslandTask;

import java.util.concurrent.*;

public class Simulator {
    private final Island island;
    private final int cycleDuration;
    private final int simulationCycles;
    private final ForkJoinPool forkJoinPool;

    public Simulator(String configPath) {
        IslandConfig config = ConfigLoader.loadIslandConfig(configPath);
        this.island = new Island(configPath);
        this.cycleDuration = config.getCycleDuration();
        this.simulationCycles = config.getSimulationCycles();

        this.forkJoinPool = new ForkJoinPool();
    }

    public void start() {

        // Выводим начальное состояние острова
        island.printIsland();

        // Ждём 7 секунд перед началом симуляции
        island.waitBeforeStart(7);

        for (int cycle = 1; cycle <= simulationCycles; cycle++) {
            System.out.println("Цикл: " + cycle);

            // Обрабатываем клетки параллельно
            forkJoinPool.invoke(new ProcessIslandTask(island));

            // Выводим статистику острова
            island.printIsland();

            // Задержка между циклами
            try {
                Thread.sleep(cycleDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        forkJoinPool.shutdown();
    }
}