package udesc.dsd.Model;

import static udesc.dsd.Commons.Colors.GREEN;
import static udesc.dsd.Commons.Colors.WHITE;

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public boolean isEmpty(){
        return car == null;
    }

    @Override
    public String toString(){
        return isEntrance? GREEN + direction.getDirectionSymbol() : WHITE + direction.getDirectionSymbol();
    }
}
