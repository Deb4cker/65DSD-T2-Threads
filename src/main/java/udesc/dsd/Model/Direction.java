package udesc.dsd.Model;

import udesc.dsd.utils.DirectionDictionary;

import javax.swing.*;

public class Direction {
    private final int to;
    private final Icon image;

    public Direction(int direction) {
        this.to = direction;
        image = DirectionDictionary.getImage(direction);
    }

    public int to() {
        return to;
    }

    public Icon getImage(){
        return image;
    }
}
