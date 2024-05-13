package udesc.dsd.View;

import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Car;
import udesc.dsd.Model.CarFactory;
import udesc.dsd.Model.EntranceMediatorRoutine;
import udesc.dsd.Model.Factory.MonitorsCellFactory;
import udesc.dsd.Model.Factory.SemaphoricCellFactory;
import udesc.dsd.Model.Road;

import javax.swing.*;

import static udesc.dsd.Commons.Constants.*;

public class HomeView extends JFrame {

    private Road road;

    public HomeView(){
        setLocationRelativeTo(null);
        setSize(420, 440);
        setVisible(true);
        setTitle("Simulação de trafego");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        lbCarCount = new JLabel("Quantos carros serão gerados?");
        lbChoseModel = new JLabel("Qual malha será utilizada?");
        lbMutualExclusion = new JLabel("Qual mecanismo de exclusão mutua será usado?");

        add(lbCarCount);
        add(lbChoseModel);
        add(lbMutualExclusion);
        add(new JLabel());

        lbCarCount.setBounds(105, 50, 200, 25);
        lbChoseModel.setBounds(105, 120, 200, 25);
        lbMutualExclusion.setBounds(70, 285, 300, 25);

        tfCarCount = new JTextField("1");
        add(tfCarCount);
        tfCarCount.setBounds(105, 75, 200, 25);

        rbModelGroup = new ButtonGroup();
        rbMutualExclusionGroup = new ButtonGroup();

        rbModel1 = new JRadioButton("Cruzamento simples");
        rbModel2 = new JRadioButton("Varios Cruzamentos");
        rbModel3 = new JRadioButton("Cruzamentos complexos");
        rbModel4 = new JRadioButton("Linha simples");
        rbModel5 = new JRadioButton("Curva simples");

        rbModelGroup.add(rbModel1);
        rbModelGroup.add(rbModel2);
        rbModelGroup.add(rbModel3);
        rbModelGroup.add(rbModel4);
        rbModelGroup.add(rbModel5);

        add(rbModel1);
        add(rbModel2);
        add(rbModel3);
        add(rbModel4);
        add(rbModel5);

        rbModel1.setBounds(110, 150, 200, 25);
        rbModel2.setBounds(110, 175, 200, 25);
        rbModel3.setBounds(110, 200, 200, 25);
        rbModel4.setBounds(110, 225, 200, 25);
        rbModel5.setBounds(110, 250, 200, 25);

        rbSemaphores = new JRadioButton("Semaforos");
        rbMonitors = new JRadioButton("Monitores");

        rbMutualExclusionGroup.add(rbSemaphores);
        rbMutualExclusionGroup.add(rbMonitors);

        add(rbSemaphores);
        add(rbMonitors);

        rbSemaphores.setBounds(110, 310, 200, 25);
        rbMonitors.setBounds(110, 335, 200, 25);

        btStart = new JButton("Iniciar");
        btStart.addActionListener(a -> init());
        add(btStart);

        btStart.setBounds(110, 390, 200, 25);
    }

    private void init(){
        String file = switchRoadFile();
        CellFactory cellFactory = rbSemaphores.isSelected()? new SemaphoricCellFactory() : rbMonitors.isSelected()? new MonitorsCellFactory() : null;
        road = new Road(file, cellFactory);

        CarFactory carFactory = new CarFactory(road);

        EntranceMediatorRoutine routine = new EntranceMediatorRoutine(road);

        int carCount = Integer.parseInt(tfCarCount.getText());

        for(int i = 0; i < carCount; i++){
            Car car = carFactory.buildCar();
            routine.addToQueue(car);
        }
        new MainView(road);

        routine.start();
    }

    private String switchRoadFile(){
        if (rbModel1.isSelected()) return ROAD_FILE_1;
        else if(rbModel2.isSelected()) return ROAD_FILE_2;
        else if (rbModel3.isSelected()) return ROAD_FILE_3;
        else if (rbModel4.isSelected()) return ROAD_FILE_TEST1;
        else if (rbModel5.isSelected()) return ROAD_FILE_TEST2;
        else return ROAD_FILE_TEST1;
    }

    private JButton btStart;
    private JTextField tfCarCount;
    private ButtonGroup rbModelGroup;
    private ButtonGroup rbMutualExclusionGroup;
    private JRadioButton rbModel1;
    private JRadioButton rbModel2;
    private JRadioButton rbModel3;
    private JRadioButton rbModel4;
    private JRadioButton rbModel5;
    private JRadioButton rbSemaphores;
    private JRadioButton rbMonitors;
    private JLabel lbCarCount;
    private JLabel lbChoseModel;
    private JLabel lbMutualExclusion;
}
