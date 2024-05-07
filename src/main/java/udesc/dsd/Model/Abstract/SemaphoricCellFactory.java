package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Direction;

public class SemaphoricCellFactory extends CellFactory{
    @Override
    public Cell createCell(int row, int col, Direction direction, boolean isEntrance) {
        return new SemaphoricCell(row, col, direction, isEntrance);
    }
}
