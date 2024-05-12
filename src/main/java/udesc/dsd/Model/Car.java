package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import javax.swing.*;

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
    private final Map<Integer, int[]> crossPossibilities = new HashMap<>();
    private boolean isRunning;

    public Car(Road road, long sleepTime, Icon carIcon) {
        this.road = road;
        this.sleepTime = sleepTime;
        this.carIcon = carIcon;
        loadPossibilities();
    }

    public void setCell(Cell cell){
        try {
            this.cell = cell;
            if (!cell.isCross()){
                this.direction = cell.getDirection();
                cell.setCar(this);
            }
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
        isRunning = true;
        while(isRunning){
            go();
        }
        System.out.println( getName() + ": Stopping");
        road.removeCar(this);
    }

    private void go(){
        try {
            goToCell(nextCell);
            Cell nextCell = findNextCell();
            if(nextCell != null){
                if(nextCell.isCross()) crossRoutine();
                setNextCell(findNextCell());
            } else shutdown();
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void shutdown(){
        isRunning = false;
    }

    private void crossRoutine() {

        //TODO
        //Ideia:
        //caso a direção escolhida é up e ele esta em up, a sequencia seria:
        goToCell(cellAtUp());
        goToCell(cellAtUp());

        //caso a direção escolhida é right  e ele esta em up
        goToCell(cellAtUp());

        //caso a direção escolhida  é left e ele esta em up
        goToCell(cellAtUp());
        goToCell(cellAtUp());
        goToCell(cellAtLeft());
    }

    private void goToCell(Cell cell){
        Cell aux = this.cell;
        setCell(cell);
        if(aux != null) aux.removeCar();
    }

    private Cell findNextCell(){
        try {
            int direction = this.direction.to();
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

    private Direction choseDirection() {
        Random random = new Random();
        int directionToGo = crossPossibilities.get(this.direction.to())[random.nextInt(4)];

        return new Direction(directionToGo);
    }

    private Cell cellAtUp(){
        return road.getCellAtUpFrom(cell);
    }

    private Cell cellAtRight(){
        return road.getCellAtRightFrom(cell);
    }

    private Cell cellAtDown(){
        return road.getCellAtDownFrom(cell);
    }

    private Cell cellAtLeft(){
        return road.getCellAtLeftFrom(cell);
    }

    public Icon getCarIcon() {
        return carIcon;
    }

    public void imInUpAndGoingToLeft(Car car){
        car.goToCell(cellAtUp());
        goToCell(cellAtUp());
        goToCell(cellAtLeft());
    }

    private void loadPossibilities(){
        crossPossibilities.put(UP, new int[]{RIGHT, UP, LEFT});
        crossPossibilities.put(RIGHT, new int[]{UP, RIGHT, DOWN});
        crossPossibilities.put(DOWN, new int[]{LEFT, DOWN, RIGHT});
        crossPossibilities.put(LEFT, new int[]{DOWN, LEFT, UP});
    }


}
