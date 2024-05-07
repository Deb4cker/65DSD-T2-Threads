package udesc.dsd.View;

import udesc.dsd.Model.Road;

import javax.swing.*;

public class MainView extends JPanel {

    private Road road;

    public MainView(Road road){
        this.road = road;

        this.setBounds(100, 100, 1000, 1000);
        this.setVisible(true);
    }



}
