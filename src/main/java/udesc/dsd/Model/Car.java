package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Interface.CrossAction;

import javax.swing.*;

import java.util.*;

import static udesc.dsd.Commons.Constants.*;

public class Car extends Thread{
    private final long sleepTime;
    private final Road road;
    private Direction direction;
    private Cell nextCell;
    private Cell cell;
    private final Icon carIcon;
    private final Map<Integer, CrossAction[]> crossPossibilities = new HashMap<>();
    private boolean isRunning;

    public Car(Road road, long sleepTime, Icon carIcon) {
        this.road = road;
        this.sleepTime = sleepTime;
        this.carIcon = carIcon;
        loadPossibilities();
    }

    public void setCell(Cell cell){
        this.cell = cell;
        if (!cell.isCross()) this.direction = cell.getDirection();
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
            } else {
                shutdown();
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void shutdown(){
        isRunning = false;
    }

    private void crossRoutine() {
        Random random = new Random();
        int option = random.nextInt(3);
        CrossAction routine = crossPossibilities.get(direction.to())[option];
        routine.doRoutine();
    }

    private void goToCell(Cell cell) throws InterruptedException{
        Cell aux = this.cell;
        setCell(cell);
        cell.setCar(this);
        if(aux != null) aux.removeCar();
        Thread.sleep(sleepTime);
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

    public void fromUpToRight(){
        Cell a = cellAtUp();
        Cell b = road.getCellAtRightFrom(a);
        philosopherDinner(a, b);
    }

    private void fromUpToUp(){
        Cell a = cellAtUp();
        Cell b = road.getCellAtUpFrom(a);
        Cell c = road.getCellAtUpFrom(b);
        philosopherDinner(a, b, c);
    }

    private void fromUpToLeft(){
        Cell a = cellAtUp();
        Cell b = road.getCellAtUpFrom(a);
        Cell c = road.getCellAtLeftFrom(b);
        Cell d = road.getCellAtLeftFrom(c);
        philosopherDinner(a, b, c, d);
    }

    private void fromRightToDown(){
        Cell a = cellAtRight();
        Cell b = road.getCellAtDownFrom(a);
        philosopherDinner(a, b);
    }

    private void fromRightToRight(){
        Cell a = cellAtRight();
        Cell b = road.getCellAtRightFrom(a);
        Cell c = road.getCellAtRightFrom(b);
        philosopherDinner(a, b, c);
    }

    private void fromRightToUp(){
        Cell a = cellAtRight();
        Cell b = road.getCellAtRightFrom(a);
        Cell c = road.getCellAtUpFrom(b);
        Cell d = road.getCellAtUpFrom(c);
        philosopherDinner(a, b, c, d);
    }

    private void fromLeftToUp(){
        Cell a = cellAtLeft();
        Cell b = road.getCellAtUpFrom(a);
        philosopherDinner(a, b);
    }

    private void fromLeftToLeft(){
        Cell a = cellAtLeft();
        Cell b = road.getCellAtLeftFrom(a);
        Cell c = road.getCellAtLeftFrom(b);
        philosopherDinner(a, b, c);
    }

    private void fromLeftToDown(){
        Cell a = cellAtLeft();
        Cell b = road.getCellAtLeftFrom(a);
        Cell c = road.getCellAtDownFrom(b);
        Cell d = road.getCellAtDownFrom(c);
        philosopherDinner(a, b, c, d);
    }

    private void fromDownToLeft(){
        Cell a = cellAtDown();
        Cell b = road.getCellAtLeftFrom(a);
        philosopherDinner(a, b);
    }

    private void fromDownToDown(){
        Cell a = cellAtDown();
        Cell b = road.getCellAtDownFrom(a);
        Cell c = road.getCellAtDownFrom(b);
        philosopherDinner(a, b, c);
    }

    private void fromDownToRight(){
        Cell a = cellAtDown();
        Cell b = road.getCellAtDownFrom(a);
        Cell c = road.getCellAtRightFrom(b);
        Cell d = road.getCellAtRightFrom(c);
        philosopherDinner(a, b, c, d);
    }

    private void philosopherDinner(Cell a, Cell b){
        Random r = new Random();

        boolean gone = false;
        try {
            do {
                boolean blockedA = a.tryBlock();
                boolean blockedB = b.tryBlock();
                if (blockedA && blockedB) {
                    goToCell(a);
                    goToCell(b);
                    gone = true;
                    this.direction = b.getDirection();
                    a.release();
                    b.release();
                } else {
                    if (blockedA) a.release();
                    if (blockedB) b.release();
                    sleep(r.nextLong(500));
                }
            } while (!gone);

        }catch (InterruptedException e){
            System.out.println("Deu pau na " + getName());
        }
    }

    private void philosopherDinner(Cell a, Cell b, Cell c){
        Random r = new Random();
        boolean gone = false;
        try{
            do {
                boolean blockedA = a.tryBlock();
                boolean blockedB = b.tryBlock();
                boolean blockedC = c.tryBlock();

                if(blockedA && blockedB && blockedC){
                    goToCell(a);
                    goToCell(b);
                    goToCell(c);
                    gone = true;
                    this.direction = c.getDirection();
                    a.release();
                    b.release();
                    c.release();
                } else {
                    if (blockedA) a.release();
                    if (blockedB) b.release();
                    if (blockedC) c.release();
                    sleep(r.nextLong(500));
                }
            } while (!gone);

        } catch (InterruptedException e){
            System.out.println("Deu pau na " + getName());
        }
    }

    private void philosopherDinner(Cell a, Cell b, Cell c, Cell d){
        Random r = new Random();
        boolean gone = false;
        try {
            do {
                boolean blockedA = a.tryBlock();
                boolean blockedB = b.tryBlock();
                boolean blockedC = c.tryBlock();
                boolean blockedD = d.tryBlock();

                if (blockedA && blockedB && blockedC && blockedD) {
                    goToCell(a);
                    goToCell(b);
                    goToCell(c);
                    goToCell(d);
                    gone = true;
                    this.direction = d.getDirection();
                    a.release();
                    b.release();
                    c.release();
                    d.release();
                } else {
                    if (blockedA) a.release();
                    if (blockedB) b.release();
                    if (blockedC) c.release();
                    if (blockedD) d.release();
                    sleep(r.nextLong(500));
                }
            } while (!gone);

        } catch (InterruptedException e){
            System.out.println("Deu pau na " + getName());
        }
    }

    private void philosophersDinner(List<Cell> cellsToGoThrough){

    }

    private void loadPossibilities(){
        crossPossibilities.put(UP, new CrossAction[]{
                this::fromUpToRight,
                this::fromUpToUp,
                this::fromUpToLeft
        });

        crossPossibilities.put(RIGHT, new CrossAction[]{
                this::fromRightToDown,
                this::fromRightToRight,
                this::fromRightToUp
        });

        crossPossibilities.put(DOWN, new CrossAction[]{
                this::fromDownToLeft,
                this::fromDownToDown,
                this::fromDownToRight
        });

        crossPossibilities.put(LEFT, new CrossAction[]{
                this::fromLeftToUp,
                this::fromLeftToLeft,
                this::fromLeftToDown
        });
    }
}
