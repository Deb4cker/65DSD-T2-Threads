package udesc.dsd;

import udesc.dsd.Model.Abstract.CellFactory;
import udesc.dsd.Model.Factory.MonitorsCellFactory;
import udesc.dsd.Model.Factory.SemaphoricCellFactory;
import udesc.dsd.Model.Car;
import udesc.dsd.Model.CarFactory;
import udesc.dsd.Model.EntranceMediatorRoutine;
import udesc.dsd.Model.Road;
import udesc.dsd.View.HomeView;
import udesc.dsd.View.MainView;

import java.util.Scanner;

import static udesc.dsd.Commons.Constants.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new HomeView();
    }
}