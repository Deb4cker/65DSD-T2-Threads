package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;

import static udesc.dsd.Commons.Colors.*;
import static udesc.dsd.Commons.Constants.*;

public class Car extends Thread{
    private final long sleepTime;
    private final Road road;
    private Direction direction;
    private Cell nextCell;
    private Cell cell;
    private final String consoleColor;

    public Car(Road road, long sleepTime, String consoleColor) {
        this.road = road;
        this.sleepTime = sleepTime;
        this.consoleColor = consoleColor;
    }

    public void setCell(Cell cell){
        this.cell = cell;
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
            if(aux != null) {
                aux.removeCar();
            }

            nextCell = findNextCell();
            if(nextCell != null) {
                System.out.println(getName() + ": I'm going to cell x" + nextCell.getRow() + ", y" + nextCell.getCol());
                clearConsole();
                road.printMatrixInConsole();
            }
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Cell findNextCell(){
        try {
            return switch (direction.to()) {
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

    private void clearConsole(){  
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    public String getColor() {
        return consoleColor;
    }
}
