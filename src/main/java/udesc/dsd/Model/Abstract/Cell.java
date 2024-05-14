package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Car;
import udesc.dsd.Model.Direction;
import udesc.dsd.Model.Observer.IconUpdater;

import java.util.concurrent.Semaphore;

public abstract class Cell {
    private final int row;
    private final int col;
    private final Direction direction;
    private final boolean isEntrance;
    private final boolean isCross;
    protected Car car;
    private IconUpdater ui;

    public Cell(int row, int col, Direction direction, boolean isEntrance, boolean isCross) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isEntrance = isEntrance;
        this.isCross = isCross;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isCross() {return isCross;}

    public boolean isCrossEnd(){
        return direction.to() > 4 && direction.to() < 9;
    }

    public void setCarAndBlock(Car car) throws InterruptedException {
        block(); //entao espere
        setCar(car);
    }

    public void setCar(Car car){
        this.car = car;
        ui.update(car.getCarIcon(), row, col);
    }

    public void removeCarAndRelease() {
        removeCar();
        release(); //pode vir
    }

    public void removeCar(){
        this.car = null; //desocupei
        ui.update(direction.getImage(), row, col);
    }

    public void setUi(IconUpdater ui){
        this.ui = ui;
    }

    public abstract void release();

    public abstract void block() throws InterruptedException;

    public abstract boolean tryBlock() throws InterruptedException;

    public Direction getDirection() {
        return direction;
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public synchronized boolean isFree(){
        return car == null;
    }

    @Override
    public String toString(){
        return direction + " [line: "+getRow() + ", " + "column: "+ getCol() +"]";
    }

    public Semaphore getSemaphore(){
        return null;
    }
}
