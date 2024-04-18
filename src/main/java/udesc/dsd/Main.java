package udesc.dsd;

import udesc.dsd.Model.Cell;
import udesc.dsd.utils.MatrixBuilder;

import java.util.Arrays;

import static udesc.dsd.Commons.Constants.ROAD_FILE_1;
import static udesc.dsd.Commons.Constants.ROAD_FILE_2;
import static udesc.dsd.Commons.Constants.ROAD_FILE_3;

public class Main {
    public static void main(String[] args){

        int fileNum = 3;
        String file = switchRoadFile(fileNum);
        Cell[][] matrix = new MatrixBuilder(file).buildMatrix();

        for (Cell[] cells : matrix) {
            Arrays.stream(cells).forEach(System.out::print);
            System.out.println();
        }
    }

    public static String switchRoadFile(int num){
        return switch (num){
            case 1 -> ROAD_FILE_1;
            case 2 -> ROAD_FILE_2;
            case 3 -> ROAD_FILE_3;
            default -> null;
        };
    }
}