package udesc.dsd.Model.Abstract;

import udesc.dsd.Model.Direction;

public abstract class CellFactory {
    public abstract Cell createCell(int row, int col, Direction direction, boolean isEntrance);
}
