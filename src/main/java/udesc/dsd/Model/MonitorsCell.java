package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorsCell extends Cell {

    private final Lock lock = new ReentrantLock();

    public MonitorsCell(int row, int col, Direction direction, boolean isEntrance) {
        super(row, col, direction, isEntrance);
    }

    @Override
    public void release() {
        lock.unlock();
    }

    @Override
    public void block() throws InterruptedException {
        lock.lock();
    }
}
