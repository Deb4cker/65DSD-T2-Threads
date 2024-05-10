package udesc.dsd.utils;

import javax.swing.*;

import static udesc.dsd.Commons.Constants.*;
import static udesc.dsd.Commons.Constants.FROM_DOWN_TO_LEFT;

public class DirectionDictionary {

    public static Icon getImage(int dir){
        return switch (dir) {
            case EMPTY -> new ImageIcon(GRASS_ICON);
            case UP, TO_UP -> new ImageIcon(UP_ROAD_ICON);
            case RIGHT, TO_RIGHT -> new ImageIcon(RIGHT_ROAD_ICON);
            case DOWN, TO_DOWN -> new ImageIcon(DOWN_ROAD_ICON);
            case LEFT, TO_LEFT -> new ImageIcon(LEFT_ROAD_ICON);
            default -> new ImageIcon(CROSS_ROAD_ICON);
        };
    }

    public static String getString(int dir){
        return switch (dir) {
            case UP -> "up";
            case RIGHT -> "right";
            case DOWN -> "down";
            case LEFT -> "left";
            case TO_UP -> "to up";
            case TO_RIGHT -> "to right";
            case TO_DOWN -> "to down";
            case TO_LEFT -> "to left";
            case FROM_UP_TO_RIGHT -> "from up to right";
            case FROM_UP_TO_LEFT -> "from up to left";
            case FROM_RIGHT_TO_DOWN -> "from right to down";
            case FROM_DOWN_TO_LEFT -> "from down to left";
            default -> "not a direction";
        };
    }

    public static String getSymbol(int dir){
        return switch (dir) {
            case UP, TO_UP -> "↑\t";
            case RIGHT, TO_RIGHT -> "→\t";
            case DOWN, TO_DOWN -> "↓\t";
            case LEFT, TO_LEFT -> "←\t";
            case FROM_UP_TO_RIGHT -> "↑→\t";
            case FROM_UP_TO_LEFT -> "↑←\t";
            case FROM_RIGHT_TO_DOWN -> "→↓\t";
            case FROM_DOWN_TO_LEFT -> "←↓\t";
            default -> "-\t";
        };
    }
}
