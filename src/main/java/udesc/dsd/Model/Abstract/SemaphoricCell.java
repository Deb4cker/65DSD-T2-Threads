package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Car;
import udesc.dsd.Model.Direction;

import java.util.concurrent.Semaphore;

public class SemaphoricCell extends Cell{

    private final Semaphore semaphore = new Semaphore(1);

    public SemaphoricCell(int row, int col, Direction direction, boolean isEntrance) {
        super(row, col, direction, isEntrance);
    }

    @Override
    public void setCar(Car car) throws InterruptedException {
        block(); //entao espere
        this.car = car; //eu ocupei

    }

    @Override
    public void removeCar() {
        this.car = null; //desocupei
        release(); //pode vir
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
