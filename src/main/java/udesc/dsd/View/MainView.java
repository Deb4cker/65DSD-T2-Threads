package udesc.dsd.View;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Road;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private final Road road;
    JPanel[][] viewMatrix;

    public MainView(Road road){
        setSize(1600, 900);
        this.road = road;
        loadMatrix();
        setVisible(true);
    }

    private void loadMatrix(){
        Cell[][] roadMatrix = road.getMatrix();
        viewMatrix = new JPanel[roadMatrix.length][roadMatrix[0].length];

        for(Cell[] columns : roadMatrix){
            for (Cell cell : columns){

                int row = cell.getRow();
                int column = cell.getCol();
                Icon image = cell.getDirection().getImage();
                PointInScreen point = new PointInScreen(row + 30, column + 30);

                JPanel draw = mountVisualCell(image, point);
                viewMatrix[row][column] = draw;
                add(draw);
            }
        }
    }

    private JPanel mountVisualCell(Icon icon, PointInScreen point){
        JPanel cell = new JPanel();
        JLabel image = new JLabel();
        image.setIcon(icon);
        cell.setSize(30, 30);
        cell.setLayout(new BorderLayout());
        cell.setBounds(point.x, point.y, 30, 30);
        cell.add(image);
        return cell;
    }

    private record PointInScreen(int x, int y){}
}
