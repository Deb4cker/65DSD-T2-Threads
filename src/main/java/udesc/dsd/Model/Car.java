package udesc.dsd.Model;

import java.util.Random;

public class Car extends Thread{
    private final long sleepTime;
    private Direction direction;

    public Car() {
        Random r = new Random();
        this.sleepTime = r.nextLong(100, 1000);
    }

    public long getSleepTime(){
        return sleepTime;
    }

    public void go() {

    }

    public void goToUp() {

    }

    public void goToRight() {

    }

    public void goToDown() {

    }

    public void gotToLeft() {

    }

    public void goFromUpToRight() {

    }

    public void goFromUpToLeft() {

    }

    public void goFromRightToDown() {

    }

    public void goFromDownToLeft() {

    }
}
