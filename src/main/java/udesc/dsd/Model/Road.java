package udesc.dsd.Model;

import udesc.dsd.utils.MatrixBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Road {

    private final Cell[][] matrix;
    private final List<Car> cars = new ArrayList<>();
    private final List<Cell> entrances = Collections.synchronizedList(new ArrayList<>());

    public Road(String file) {
        this.matrix = new MatrixBuilder(file).buildMatrix();

        for (Cell[] cells : matrix) {
            for(Cell cell : cells){
                if (cell.isEntrance()) entrances.add(cell);
            }
        }
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Car> getCars() {
        return cars;
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

    public synchronized void printMatrixInConsole() {
        if(cars.isEmpty()) System.out.println("No cars to print");
        else System.out.println("Cars: " + (cars.indexOf(cars.getLast()) + 1));

        for (Cell[] cells : matrix) {
            Arrays.stream(cells).forEach(System.out::print);
            System.out.println();
        }
    }

    public Cell getCellByPosition(int x, int y) {
        return matrix[x][y];
    }
}
