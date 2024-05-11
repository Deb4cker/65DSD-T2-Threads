package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static udesc.dsd.Commons.Constants.*;

public class Car extends Thread{
    private final long sleepTime;
    private final Road road;
    private Direction direction;
    private Cell nextCell;
    private Cell cell;
    private final Icon carIcon;

    public Car(Road road, long sleepTime, Icon carIcon) {
        this.road = road;
        this.sleepTime = sleepTime;
        this.carIcon = carIcon;
    }

    public void setCell(Cell cell){
        this.cell = cell;
        //se a celula nao for cruzamento
        this.direction = cell.getDirection();
        try {
            cell.setCar(this);
        } catch (InterruptedException e){
            System.out.println("Ferrou :(");
        }
    }

    public void removeFromCell(){
        cell.removeCar();
        this.cell = null;
    }

    public void setNextCell(Cell nextCell){
        this.nextCell = nextCell;
    }

    @Override
    public void run() {
        while(nextCell != null){
            go();
        }
        System.out.println( getName() + ": Stopping");
        road.removeCar(this);
    }

    private void go(){
        try {
            Cell aux = cell;
            setCell(nextCell);
            if(aux != null) {aux.removeCar();}

            nextCell = findNextCell();
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Cell findNextCell(){
        try {
            int direction = this.direction.to();
            if(direction > 4) return getCrossCellPossibilities();
            return switch (direction) {
                case UP -> cellAtUp();
                case RIGHT -> cellAtRight();
                case DOWN -> cellAtDown();
                case LEFT -> cellAtLeft();
                default -> null;
            };
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private Cell getCrossCellPossibilities() {
        return null;
    }

    private Cell cellAtUp() throws ArrayIndexOutOfBoundsException{
        int x = cell.getRow();
        int y = cell.getCol();
        return road.getCellByPosition(x - 1, y);
    }

    private Cell cellAtRight() throws ArrayIndexOutOfBoundsException{
        int x = cell.getRow();
        int y = cell.getCol();
        return road.getCellByPosition(x, y + 1);
    }

    private Cell cellAtDown() throws ArrayIndexOutOfBoundsException{
        int x = cell.getRow();
        int y = cell.getCol();
        return road.getCellByPosition(x + 1, y);
    }

    private Cell cellAtLeft() throws ArrayIndexOutOfBoundsException{
        int x = cell.getRow();
        int y = cell.getCol();
        return road.getCellByPosition(x, y - 1);
    }

    public Icon getCarIcon() {
        return carIcon;
    }
}
