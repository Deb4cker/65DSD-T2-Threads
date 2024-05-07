package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import java.util.concurrent.Semaphore;

public class SemaphoricCell extends Cell {

    private final Semaphore semaphore = new Semaphore(1);

    public SemaphoricCell(int row, int col, Direction direction, boolean isEntrance) {
        super(row, col, direction, isEntrance);
    }

    @Override
    public void release() {
        semaphore.release();
    }

    @Override
    public void block() throws InterruptedException {
        semaphore.acquire();
    }


}
