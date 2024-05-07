package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Direction;
import udesc.dsd.Model.MonitorsCell;

public class MonitorsCellFactory extends CellFactory{
    @Override
    public Cell createCell(int row, int col, Direction direction, boolean isEntrance) {
        return new MonitorsCell(row, col, direction, isEntrance);
    }
}
