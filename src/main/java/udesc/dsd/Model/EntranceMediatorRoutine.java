package udesc.dsd.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntranceMediatorRoutine extends Thread{

    private final Road road;
    private boolean running = false;
    private final List<Car> queue = Collections.synchronizedList(new ArrayList<>());

    public EntranceMediatorRoutine(Road road) {
        this.road = road;
    }

    public void addToQueue(Car car) {
        queue.add(car);
    }

    public void removeFromQueue(Car car) {
        queue.remove(car);
    }

    @Override
    public void run() {
        running = true;
        Car car;

        while(running) {
            Cell entrance = road.getFreeEntrance();

            if(!queue.isEmpty()) {
                car = queue.getFirst();
                car.setNextCell(entrance);
                car.start();
                queue.remove(car);
                road.addCar(car);

                if(queue.isEmpty()) shutDown();
            }
        }
    }

    public void shutDown(){
        running = false;
    }
}
