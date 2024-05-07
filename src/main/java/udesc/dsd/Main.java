package udesc.dsd;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Abstract.MonitorsCellFactory;
import udesc.dsd.Model.Abstract.SemaphoricCellFactory;
import udesc.dsd.Model.Car;
import udesc.dsd.Model.CarFactory;
import udesc.dsd.Model.EntranceMediatorRoutine;
import udesc.dsd.Model.Road;
import udesc.dsd.View.MainView;

import java.util.Scanner;

import static udesc.dsd.Commons.Constants.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println(""" 
                What file do you want to read?"
                [1]: Model 1
                [2]: Model 2
                [3]: Model 3
                [4]: Straight Line Test
                [5]: Curve Behavior Test
                """);

        int fileNum = scanner.nextInt();

        System.out.print("\nHow many cars do you want to run? ");
        int carNumber = scanner.nextInt();

        System.out.print("\nRun routine [true], or print the matrix? [false]: ");
        boolean wantToRunRoutine = scanner.nextBoolean();

        System.out.print("\nSemaphore [true], or Monitors? [false]: ");
        boolean isSemaphoric = scanner.nextBoolean();
        CellFactory factory = isSemaphoric? new SemaphoricCellFactory() : new MonitorsCellFactory();

        if (wantToRunRoutine) routineTest(fileNum, carNumber, factory);
        else matrixPrintTest(fileNum, factory);

        scanner.close();


    }

    static void routineTest(int fileNum, int carNumber, CellFactory factory) throws InterruptedException {
        String file = switchRoadFile(fileNum);
        Road road = new Road(file, factory);
        CarFactory carFactory = new CarFactory(road);

        road.printMatrixInConsole();

        EntranceMediatorRoutine routine = new EntranceMediatorRoutine(road);

        for(int i = 0; i < carNumber; i++){
            Car car = carFactory.buildCar();
            routine.addToQueue(car);
        }

        new MainView(road).setVisible(true);

        routine.start();
        routine.join();
    }

    static void matrixPrintTest(int fileNum, CellFactory factory){
        String file = switchRoadFile(fileNum);
        Road road = new Road(file, factory);

        road.printMatrixInConsole();
    }

    static String switchRoadFile(int num){
        return switch (num){
            case 1 -> ROAD_FILE_1;
            case 2 -> ROAD_FILE_2;
            case 3 -> ROAD_FILE_3;
            case 5 -> ROAD_FILE_TEST2;
            default -> ROAD_FILE_TEST1;
        };
    }
}