import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Runner {

    final static int n_threads = 8;
    final static int interval = 10;
    final static int n_readings = 5;
    final static int max = 70;
    final static int min = -100;

    static int[][] top5Cluster;
    static int[][] min5Cluster;
    static int[][] tempDiffInterval;

    static SensorThread[] sensors;
    static Report report;

    public static void main(String[] args) {

        // System.out.println("Part 2");
        top5Cluster = new int[n_threads][5];
        min5Cluster = new int[n_threads][5];
        tempDiffInterval = new int[n_threads][interval];

        sensors = new SensorThread[n_threads];
        report = new Report(interval, max, min);

        for (int i = 0; i < n_threads; ++i) {
            top5Cluster[i] = new int[5];
            min5Cluster[i] = new int[5];
            tempDiffInterval[i] = new int[interval];

            sensors[i] = new SensorThread(top5Cluster[i], min5Cluster[i] , tempDiffInterval[i], report);
        }

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(n_threads);

        for (int i = 0; i < n_readings; ++i) {

            for (int j = 0; j < n_threads; ++j) {
                executorService.execute(sensors[i]);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            report.displayReport();
            report.flushBuffers();
        }

        long end = System.currentTimeMillis();

        System.out.println("\nTotal time: " + (end - start) + "ms");

        System.err.println("Shutting down executor service...");
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