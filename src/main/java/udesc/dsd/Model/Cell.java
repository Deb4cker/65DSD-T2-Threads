package udesc.dsd.Model;

import static udesc.dsd.Commons.Colors.*;

public class Cell {

    private final int row;
    private final int col;
    private final Direction direction;
    private final boolean isEntrance;
    private Car car;

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

    public synchronized void setCar(Car car) {
        this.car = car;
    }

    public synchronized void removeCar(){
        this.car = null;
    }

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
        if(car!= null) return BLUE + direction.getDirectionSymbol();
        return isEntrance? GREEN + direction.getDirectionSymbol() : WHITE + direction.getDirectionSymbol();
    }
}
