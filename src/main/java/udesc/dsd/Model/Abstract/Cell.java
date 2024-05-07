package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Car;
import udesc.dsd.Model.Direction;

import java.util.Random;

import static udesc.dsd.Commons.Colors.*;

public abstract class Cell {
    private final int row;
    private final int col;
    private final Direction direction;
    private final boolean isEntrance;
    protected Car car;

    public Cell(int row, int col, Direction direction, boolean isEntrance) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isEntrance = isEntrance;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract void setCar(Car car) throws InterruptedException;

    public abstract void removeCar();

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

    @Override
    public String toString(){
        if(car!= null) return car.getColor() + direction.getDirectionSymbol();
        return isEntrance? GREEN + direction.getDirectionSymbol() : WHITE + direction.getDirectionSymbol();
    }
}
