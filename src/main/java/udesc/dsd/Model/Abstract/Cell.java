package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Car;
import udesc.dsd.Model.Direction;
import udesc.dsd.Model.Observer.IconUpdater;
import udesc.dsd.Model.Road;

public abstract class Cell {
    private final int row;
    private final int col;
    private final Direction direction;
    private final boolean isEntrance;
    private final boolean isCross;
    protected Car car;
    private IconUpdater ui;
    private Road road;

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

    public void setRoad(Road road){
        this.road = road;
    }

    public void removeCar() {
        this.car = null; //desocupei
        ui.update(direction.getImage(), row, col);
        release(); //pode vir
    }

    public void setUi(IconUpdater ui){
        this.ui = ui;
    }

    private Cell cellAtUp(){
        return road.getCellAtUpFrom(this);
    }

    private Cell cellAtRight(){
        return road.getCellAtRightFrom(this);
    }

    private Cell cellAtDown(){
        return road.getCellAtDownFrom(this);
    }

    private Cell cellAtLeft(){
        return road.getCellAtLeftFrom(this);
    }

    public boolean cellAtUpIsCross(){
        Cell cell = cellAtUp();
        return cell != null && cell.isCross();
    }

    public boolean cellAtDownIsCross(){
        Cell cell = cellAtDown();
        return cell != null && cell.isCross();
    }

    public boolean cellAtLeftIsCross(){
        Cell cell = cellAtLeft();
        return cell != null && cell.isCross();    }

    public boolean cellAtRightIsCross(){
        Cell cell = cellAtRight();
        return cell != null && cell.isCross();
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
}
