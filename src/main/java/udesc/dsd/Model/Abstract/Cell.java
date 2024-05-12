package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Car;
import udesc.dsd.Model.Direction;
import udesc.dsd.Model.Observer.IconUpdater;

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

    public void setCar(Car car) throws InterruptedException {
        block(); //entao espere
        this.car = car;//eu ocupei
        ui.update(car.getCarIcon(), row, col);
    }

    public void removeCar() {
        this.car = null; //desocupei
        ui.update(direction.getImage(), row, col);
        release(); //pode vir
    }

    public void setUi(IconUpdater ui){
        this.ui = ui;
    }

    public abstract void release();

    public abstract void block() throws InterruptedException;

    public Direction getDirection() {
        return direction;
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public synchronized boolean isFree(){
        return car == null;
    }
}
