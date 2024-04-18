package udesc.dsd.Model;

import udesc.dsd.utils.DirectionDictionary;

import static udesc.dsd.Commons.Constants.*;

public class Direction {
    private final int to;
    private final String directionString;
    private final String directionSymbol;

    public Direction(int direction) {
        this.to = direction;
        directionString = DirectionDictionary.getString(direction);
        directionSymbol = DirectionDictionary.getSymbol(direction);
    }

    public int to() {
        return to;
    }

    public String toString() {
        return directionString + " " + directionSymbol;
    }

    public String getDirectionString() {
        return directionString;
    }

    public String getDirectionSymbol() {
        return directionSymbol;
    }
}
