import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Implement 8 threads
// TODO: include write to memory

class Runner {

    public static void main(String[] args) {

        System.out.println("Part 2");

        SensorThread sensor = new SensorThread();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(sensor);

        executorService.shutdown();
        while (!executorService.isTerminated()) { 
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }   
    }
}