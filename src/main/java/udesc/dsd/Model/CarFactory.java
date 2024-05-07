package udesc.dsd.Model;

import java.util.Random;

import static udesc.dsd.Commons.Colors.*;

public class CarFactory {

    private final Road road;
    private final Random rand = new Random();
    private final String[] colors = { BLUE, YELLOW, PURPLE, RED, CYAN };
    public CarFactory(Road road) {
        this.road = road;
    }

    public Car buildCar(){
        long sleepTime = rand.nextLong(100, 1000);
        return new Car(road, sleepTime, colors[rand.nextInt(5)]);
    }
}
