package udesc.dsd.Model.Factory;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Direction;
import udesc.dsd.Model.MonitorsCell;

public class MonitorsCellFactory extends CellFactory {
    @Override
    public Cell createCell(int row, int col, Direction direction, boolean isEntrance, boolean isCross) {
        return new MonitorsCell(row, col, direction, isEntrance, isCross);
    }
}
