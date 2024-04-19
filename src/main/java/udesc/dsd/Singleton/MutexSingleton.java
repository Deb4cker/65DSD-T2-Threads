package udesc.dsd.Singleton;

import java.util.concurrent.Semaphore;

public class MutexSingleton {

    private static MutexSingleton instance;
    private final Semaphore semaphore;

    private MutexSingleton(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public static MutexSingleton getInstance(){
        if(instance == null) instance = new MutexSingleton(new Semaphore(1));
        return instance;
    }

    public Semaphore getSemaphore(){
        return semaphore;
    }

    public static void acquire(){
        try {
            MutexSingleton.getInstance().getSemaphore().acquire();
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public static void release(){
        MutexSingleton.getInstance().getSemaphore().release();
    }
}
