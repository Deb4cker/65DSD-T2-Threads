package udesc.dsd.Model;

import udesc.dsd.utils.DirectionDictionary;

import javax.swing.*;

public class Direction {
    private final int to;
    private final String directionString;
    private final String directionSymbol;
    private final Icon image;

    public Direction(int direction) {
        this.to = direction;
        directionString = DirectionDictionary.getString(direction);
        directionSymbol = DirectionDictionary.getSymbol(direction);
        image = DirectionDictionary.getImage(direction);
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

    public Icon getImage(){
        return image;
    }
}
