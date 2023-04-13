import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Math;

public class Report {
    
    int interval;
    int max;
    int min;

    int[] top5;
    int[] min5;
    int[] tempDiffInterval;

    Lock lock;

    Report(int interval, int max, int min) { 
        top5 = new int[5];
        min5 = new int[5];
        tempDiffInterval = new int[interval];

        this.interval = interval;
        lock = new ReentrantLock(); 

        for (int i = 0; i < 5; ++i) {
            top5[i] = min;
            min5[i] = max;
        }
        for (int i = 0; i < interval; ++i) tempDiffInterval[i] = 0;
    }

    public void buildReport(int[] top5, int[] min5, int[] tempDiffInterval) {
        lock.lock();
        try {
            // get the top5 temperatures
            for (int i = 5 - 1; i >= 0; --i) {
                if (this.top5[i] < top5[i]) this.top5[i] = top5[i];
                if (this.min5[i] > min5[i]) this.min5[i] = min5[i];
            }

            if (getIntervalDiff(this.tempDiffInterval) < getIntervalDiff(tempDiffInterval)) {
                for (int i = 0; i < interval; ++i) 
                    this.tempDiffInterval[i] = tempDiffInterval[i];
            }
        } finally { lock.unlock(); }
    }

    public void displayReport() {
        System.out.println("Hourly report:");
        System.out.println("top 5 temperatures-");
        for (int i = 0; i < 5; ++i) System.out.printf("%6dF", top5[i]);
        System.out.println();

        System.out.println("min 5 temperatures-");
        for (int i = 0; i < 5; ++i) System.out.printf("%6dF", min5[i]);
        System.out.println();

        System.out.println("10 minute interval with the greatest temperature difference-");
        for (int i = 0; i < interval; ++i) System.out.printf("%6dF", tempDiffInterval[i]);
        System.out.println();
    }

    int getIntervalDiff(int[] tempDiffInterval) {
        return Math.abs(tempDiffInterval[0] - tempDiffInterval[interval - 1]);
    }
}
