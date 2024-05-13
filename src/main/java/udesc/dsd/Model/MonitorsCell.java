package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorsCell extends Cell {

    private final Lock lock = new ReentrantLock();

    public MonitorsCell(int row, int col, Direction direction, boolean isEntrance, boolean isCross) {
        super(row, col, direction, isEntrance, isCross);
    }

    @Override
    public void release() {
        lock.unlock();
    }

    @Override
    public void block() throws InterruptedException {
        lock.lock();
    }

    @Override
    public synchronized boolean tryBlock() throws InterruptedException {
        Random r = new Random();
        return lock.tryLock(r.nextInt(500), TimeUnit.MILLISECONDS);
    }
}
