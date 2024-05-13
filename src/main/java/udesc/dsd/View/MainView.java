package udesc.dsd.View;

import udesc.dsd.Model.Abstract.Cell;
import udesc.dsd.Model.Observer.IconUpdater;
import udesc.dsd.Model.Road;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainView extends JFrame implements IconUpdater {

    private final Road road;
    private JLabel[][] viewMatrix;

    public MainView(Road road){
        road.mapIconUpdater(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1600, 900);
        getContentPane().setBackground(Color.DARK_GRAY);
        this.road = road;
        loadMatrix();
        setVisible(true);
    }

    private void loadMatrix(){
        Cell[][] roadMatrix = road.getMatrix();
        viewMatrix = new JLabel[roadMatrix.length][roadMatrix[0].length];
        JPanel matrixPanel = new JPanel();
        matrixPanel.setLayout(null);
        matrixPanel.setBackground(Color.DARK_GRAY);
        for(Cell[] columns : roadMatrix){
            for (Cell cell : columns){

                int row = cell.getRow();
                int column = cell.getCol();
                Icon image = cell.getDirection().getImage();
                PointInScreen point = new PointInScreen(column * 30, row * 30);

                JLabel draw = mountVisualCell(image, point);
                viewMatrix[row][column] = draw;
                matrixPanel.add(draw);
            }
        }
        matrixPanel.setBounds(0, 0, viewMatrix[0].length * 30, viewMatrix.length * 30);
        add(matrixPanel);
    }

    private JLabel mountVisualCell(Icon icon, PointInScreen point){
        JLabel image = new JLabel();
        Border two = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
        image.setBorder(two);
        image.setIcon(icon);
        image.setBounds(point.x, point.y, 30, 30);
        return image;
    }

    @Override
    public synchronized void update(Icon icon, int row, int col) {
        JLabel label = viewMatrix[row][col];
        label.setIcon(icon);
    }

    private record PointInScreen(int x, int y){}
}
