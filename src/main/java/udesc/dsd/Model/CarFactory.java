package udesc.dsd.Model;

import java.util.Random;

public class CarFactory {

    private final Road road;
    private final Random rand = new Random();

    public CarFactory(Road road) {
        this.road = road;
    }

    public Car buildCar(){
        long sleepTime = rand.nextLong(100, 1000);
        return new Car(road, sleepTime);
    }
}
