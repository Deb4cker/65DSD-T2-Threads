package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoricCell extends Cell {

    private final Semaphore semaphore = new Semaphore(1);

    public SemaphoricCell(int row, int col, Direction direction, boolean isEntrance, boolean isCross) {
        super(row, col, direction, isEntrance, isCross);
    }

    @Override
    public void release() {
        semaphore.release();
    }

    @Override
    public void block() throws InterruptedException {
        semaphore.acquire();
    }

    @Override
    public boolean tryBlock() throws InterruptedException {
        Random r = new Random();
        return semaphore.tryAcquire(r.nextInt(500), TimeUnit.MILLISECONDS);
    }

    @Override
    public Semaphore getSemaphore() {
        return semaphore;
    }
}
