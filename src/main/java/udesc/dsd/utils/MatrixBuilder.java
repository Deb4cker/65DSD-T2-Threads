package udesc.dsd.utils;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Direction;

import java.io.*;

import static udesc.dsd.Commons.Constants.*;

public class MatrixBuilder {

    private BufferedReader reader;
    private InputStream inputStream;
    private Cell[][] matrix;
    private CellFactory factory;


    public MatrixBuilder(String filename, CellFactory factory) {
        setFile(filename);
        this.factory = factory;
    }

    public void setFile(String filename) {
        inputStream = MatrixBuilder.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream != null) reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public Cell[][] buildMatrix(){

        try{
            int cols;
            int lines = 0;
            int columnCounter = 0;
            int lineCounter = 0;
            String[] values;
            String line = reader.readLine();

            while(line != null){
                values = line.split("\t");

                if(lineCounter < 2){
                    if (lineCounter == 0) lines = Integer.parseInt(values[0]);
                    else{
                        cols = Integer.parseInt(values[0]);
                        matrix = new Cell[lines][cols];
                    }

                    lineCounter++;
                    line = reader.readLine();
                    continue;
                }

                assert matrix != null;

                for(String value : values){
                    int numValue = Integer.parseInt(value);
                    int cellLine = lineCounter - 2;
                    Direction direction = new Direction(numValue);
                    boolean isEntrance = isEntrance(direction.to(), cellLine, columnCounter);

                    Cell cell = factory.createCell(cellLine, columnCounter, direction, isEntrance);

                    matrix[cell.getRow()][cell.getCol()] = cell;
                    columnCounter++;
                }

                lineCounter++;
                columnCounter = 0;
                line = reader.readLine();
            }

        } catch (IOException e){
            return new Cell[0][0];
        }

        return matrix;
    }

    private boolean isEntrance(int direction, int row, int col){
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean isTopEntrance = direction == DOWN && row == 0;
        boolean isBottomEntrance = direction == UP && row == rows - 1;
        boolean isLeftEntrance = direction == RIGHT && col == 0;
        boolean isRightEntrance = direction == LEFT && col == cols - 1;

        return isTopEntrance || isBottomEntrance || isLeftEntrance || isRightEntrance;
    }
}