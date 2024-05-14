package udesc.dsd.Model;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Observer.IconUpdater;
import udesc.dsd.utils.MatrixBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static udesc.dsd.Commons.Constants.*;

public class Road {

    private final Cell[][] matrix;
    private final List<Car> cars = new ArrayList<>();
    private final List<Cell> entrances = Collections.synchronizedList(new ArrayList<>());

    public Road(String file, CellFactory factory) {
        this.matrix = new MatrixBuilder(file, factory).buildMatrix();

        for (Cell[] cells : matrix) {
            for(Cell cell : cells){
                if (cell.isEntrance()) entrances.add(cell);
            }
        }
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public synchronized Cell getFreeEntrance(){
        while(true)
            for (Cell entrance : entrances)
                if (entrance.isFree()) return entrance;
    }

    public void removeCar(Car car) {
        car.setNextCell(null);
        car.removeFromCell();
        cars.remove(car);
    }

    public void mapIconUpdater(IconUpdater ui){
        for (Cell[] cells : matrix) {
            for(Cell cell : cells){
                cell.setUi(ui);
            }
        }
    }

    public Cell getCellByPosition(int x, int y) {
        return matrix[x][y];
    }

    public Cell[][] getMatrix(){
        return matrix;
    }

    public Cell getCellAtUpFrom(Cell cell){
        try{

            int x = cell.getRow();
            int y = cell.getCol();
            return getCellByPosition(x - 1, y);

        } catch (ArrayIndexOutOfBoundsException ex){
            return null;
        }
    }

    public Cell getCellAtDownFrom(Cell cell){
        try{
            int x = cell.getRow();
            int y = cell.getCol();
            return getCellByPosition(x + 1, y);
        } catch (ArrayIndexOutOfBoundsException ex){
            return null;
        }
    }

    public Cell getCellAtRightFrom(Cell cell){
        try{
            int x = cell.getRow();
            int y = cell.getCol();
            return getCellByPosition(x, y + 1);
        } catch (ArrayIndexOutOfBoundsException ex){
            return null;
        }
    }

    public Cell getCellAtLeftFrom(Cell cell){
        try{
            int x = cell.getRow();
            int y = cell.getCol();
            return getCellByPosition(x, y - 1);
        } catch (ArrayIndexOutOfBoundsException ex){
            return null;
        }
    }

    public Cell getCellAtFrom(int direction, Cell cell){
       return switch (direction){
           case UP -> getCellAtUpFrom(cell);
           case DOWN -> getCellAtDownFrom(cell);
           case RIGHT -> getCellAtRightFrom(cell);
           case LEFT -> getCellAtLeftFrom(cell);
           default -> null;
       };
    }
}
