package entities.сonfig;

import entities.Animal;
import entities.Cell;
import entities.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ProcessIslandTask extends RecursiveAction {
    private static final int THRESHOLD = 10;
    private final Island island;
    private final int startRow;
    private final int endRow;

    public ProcessIslandTask(Island island) {
        this(island, 0, island.getRows());
    }

    private ProcessIslandTask(Island island, int startRow, int endRow) {
        this.island = island;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    protected void compute() {
        if (endRow - startRow <= THRESHOLD) {
            for (int row = startRow; row < endRow; row++) {
                for (int col = 0; col < island.getCols(); col++) {
                    Cell cell = island.getCell(row, col);

                    synchronized (cell) {
                        processCell(cell);
                    }
                }
            }
        } else {
            int mid = (startRow + endRow) / 2;
            invokeAll(
                    new ProcessIslandTask(island, startRow, mid),
                    new ProcessIslandTask(island, mid, endRow)
            );
        }
    }

    public void processCell(Cell cell) {
        // Растения растут перед действиями животных
        cell.growPlants(0.1, 10.0); // Например, рост на 0.1 кг, максимум 10 кг

        List<Animal> animals = new ArrayList<>(cell.getAnimals());
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;

            synchronized (animal) {
                animal.performActions(); // Животное выполняет свои действия
            }
        }

        // Удаляем мёртвых животных
        cell.removeDeadAnimals();
    }
}
