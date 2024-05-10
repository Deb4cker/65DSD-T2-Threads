package udesc.dsd.Model;

import javax.swing.*;
import java.util.Random;

import static udesc.dsd.Commons.Constants.*;

public class CarFactory {

    private final Road road;
    private final Random rand = new Random();

    private final Icon[] colors = {
            new ImageIcon(BLUE_CAR_ICON),
            new ImageIcon(YELLOW_CAR_ICON),
            new ImageIcon(PURPLE_CAR_ICON),
            new ImageIcon(RED_CAR_ICON),
            new ImageIcon(GREEN_CAR_ICON)
    };

    public CarFactory(Road road) {
        this.road = road;
    }

    public Car buildCar(){
        long sleepTime = rand.nextLong(100, 1000);
        return new Car(road, sleepTime, colors[rand.nextInt(5)]);
    }
}
