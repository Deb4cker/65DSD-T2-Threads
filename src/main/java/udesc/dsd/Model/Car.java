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
        cell.removeCarAndRelease();
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
            System.out.println("Deu pau na " + getName());
        }
    }

    private void shutdown(){
        isRunning = false;
    }

    private void crossRoutine() {
        Random random = new Random();
        int direction = this.direction.to();
        int option = random.nextInt(3);
        CrossAction routine = crossPossibilities.get(direction)[option];
        routine.doRoutine();
    }

    private void goToCell(Cell cell){
        try {
            Cell aux = this.cell;
            setCell(cell);
            cell.setCarAndBlock(this);
            if (aux != null) aux.removeCarAndRelease();
            Thread.sleep(sleepTime);
        }catch (InterruptedException e){
            System.out.println("Deu pau na " + getName());
        }
    }

    private void goToCellInCross(Cell cell){
        try {
            Cell aux = this.cell;
            setCell(cell);
            cell.setCar(this);
            if (aux != null) aux.removeCar();
            Thread.sleep(sleepTime);
        }catch (InterruptedException e){
            System.out.println("Deu pau na " + getName());
        }
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

        if(a.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b);
    }

    private void fromUpToUp(){
        Cell a = cellAtUp();
        Cell b = road.getCellAtUpFrom(a);
        Cell c = road.getCellAtUpFrom(b);

        if(b.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b, c);
    }

    private void fromUpToLeft(){
        Cell a = cellAtUp();
        Cell b = road.getCellAtUpFrom(a);
        Cell c = road.getCellAtLeftFrom(b);
        Cell d = road.getCellAtLeftFrom(c);

        if(c.isCrossEnd()) crossRoutine();
        else  philosopherDinner(a, b, c, d);
    }

    private void fromRightToDown(){
        Cell a = cellAtRight();
        Cell b = road.getCellAtDownFrom(a);

        if(a.isCrossEnd())crossRoutine();
        else philosopherDinner(a, b);
    }

    private void fromRightToRight(){
        Cell a = cellAtRight();
        Cell b = road.getCellAtRightFrom(a);
        Cell c = road.getCellAtRightFrom(b);

        if(b.isCrossEnd())crossRoutine();
        else philosopherDinner(a, b, c);
    }

    private void fromRightToUp(){
        Cell a = cellAtRight();
        Cell b = road.getCellAtRightFrom(a);
        Cell c = road.getCellAtUpFrom(b);
        Cell d = road.getCellAtUpFrom(c);

        if(c.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b, c, d);
    }

    private void fromLeftToUp(){
        Cell a = cellAtLeft();
        Cell b = road.getCellAtUpFrom(a);
        if(a.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b);
    }

    private void fromLeftToLeft(){
        Cell a = cellAtLeft();
        Cell b = road.getCellAtLeftFrom(a);
        Cell c = road.getCellAtLeftFrom(b);

        if(b.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b, c);
    }

    private void fromLeftToDown(){
        Cell a = cellAtLeft();
        Cell b = road.getCellAtLeftFrom(a);
        Cell c = road.getCellAtDownFrom(b);
        Cell d = road.getCellAtDownFrom(c);

        if(c.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b, c, d);
    }

    private void fromDownToLeft(){
        Cell a = cellAtDown();
        Cell b = road.getCellAtLeftFrom(a);

        if(a.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b);
    }

    private void fromDownToDown(){
        Cell a = cellAtDown();
        Cell b = road.getCellAtDownFrom(a);
        Cell c = road.getCellAtDownFrom(b);
;
        if(b.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b, c);
    }

    private void fromDownToRight(){
        Cell a = cellAtDown();
        Cell b = road.getCellAtDownFrom(a);
        Cell c = road.getCellAtRightFrom(b);
        Cell d = road.getCellAtRightFrom(c);

        if(c.isCrossEnd()) crossRoutine();
        else philosopherDinner(a, b, c, d);
    }

    private void philosopherDinner(Cell a, Cell b){
        Random r = new Random();

        boolean gone = false;
        try {
            do {
                boolean blockedA = a.tryBlock();
                boolean blockedB = b.tryBlock();
                if (blockedA && blockedB) {
                    if (this.cell instanceof SemaphoricCell) {
                        this.cell.release();
                        goToCellInCross(a);
                        goToCellInCross(b);
                    }else {
                        goToCell(a);
                        goToCell(b);
                    }
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
                    if (this.cell instanceof SemaphoricCell){
                        this.cell.release();
                        goToCellInCross(a);
                        goToCellInCross(b);
                        goToCellInCross(c);
                    }else {
                        goToCell(a);
                        goToCell(b);
                        goToCell(c);
                    }
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
                    if (this.cell instanceof SemaphoricCell){
                        this.cell.release();
                        goToCellInCross(a);
                        goToCellInCross(b);
                        goToCellInCross(c);
                        goToCellInCross(d);

                    }else {
                        goToCell(a);
                        goToCell(b);
                        goToCell(c);
                        goToCell(d);
                    }
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

    private void philosopherDinner(List<Cell> cellsToGoThrough){
        //TODO
        //Encontrar problemas de IllegalMonitorStateException

        Random r = new Random();
        boolean gone = false;

        int cellsToLockCount = cellsToGoThrough.size();
        List<Cell> blockedCells = new ArrayList<>();
        try {
            do {
                for (Cell cell : cellsToGoThrough)
                    if (cell.tryBlock()) blockedCells.add(cell);

                boolean lockedAllCells = cellsToLockCount == blockedCells.size();

                if (lockedAllCells) {
                    blockedCells.forEach(this::goToCellInCross);
                    gone = true;
                    this.direction = blockedCells.getFirst().getDirection();
                    blockedCells.forEach(Cell::release);

                } else {
                    blockedCells.forEach(Cell::release);
                    sleep(r.nextLong(500));
                }
            } while (!gone);
        }catch (InterruptedException e){
            System.out.println("Deu pau na " + getName());
        }
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

        //entre 5 e 8 so as duas ultimas
        //se é de 2 vias(opções) so as duas primeiras opções;
    }
}
