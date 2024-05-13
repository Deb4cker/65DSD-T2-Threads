package udesc.dsd.Model;

import udesc.dsd.utils.DirectionDictionary;

import javax.swing.*;

import static udesc.dsd.Commons.Constants.*;

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

    @Override
    public String toString(){
        return switch (to){
            case UP -> "Up";
            case DOWN -> "Down";
            case RIGHT -> "Right";
            case LEFT -> "Left";
            default -> "cross";
        };
    }
}
