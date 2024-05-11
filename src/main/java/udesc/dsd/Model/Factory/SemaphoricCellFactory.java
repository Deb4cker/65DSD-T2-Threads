package udesc.dsd.Model.Factory;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Direction;
import udesc.dsd.Model.SemaphoricCell;

public class SemaphoricCellFactory extends CellFactory {
    @Override
    public Cell createCell(int row, int col, Direction direction, boolean isEntrance, boolean isCross) {
        return new SemaphoricCell(row, col, direction, isEntrance, isCross);
    }
}
