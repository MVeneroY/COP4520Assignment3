import java.util.Random;
import java.lang.Math;
import java.util.Arrays;   

public class SensorThread implements Runnable {
    
    Random rand;
    final int min = -100;
    final int max = 70;
    final int minutes = 60;
    final int interval = 10;

    int[] readings;
    int[] top5;
    int[] min5;
    int[] tempDiffInterval;
    Report report;

    SensorThread(int[] top5, int[] min5, int[] tempDiffInterval, Report report) {
        rand = new Random();
        this.readings = new int[minutes];

        this.top5 = top5;
        this.min5 = min5;
        this.tempDiffInterval = tempDiffInterval;
        this.report  = report;
    }

    @Override
    public void run() {
        for (int i = 0; i < minutes; ++i) {
            readings[i] = getTemperatureReading();
        }
        getLargestTempDifferenceInterval();

        Arrays.sort(readings);
        getHighestTemperatures();
        getLowestTemperatures();

        // displayData();
        report.buildReport(top5, min5, tempDiffInterval);
    }

    int getTemperatureReading() {
        int abs = rand.nextInt(max - min + 1);
        return abs + min;
    }

    // Precondition: readings array is sorted
    void getHighestTemperatures() {
        for (int i = 0; i < 5; ++i) top5[i] = readings[minutes-5+i];
    }

    // Precondition: readings array is sorted
    void getLowestTemperatures() {
        for (int i = 0; i < 5; ++i) min5[i] = readings[i];
    }

    // Precondition: readings are in the order they were recorded in
    void getLargestTempDifferenceInterval() {
        int start = 0;
        int maxDiff = 0;
        int maxDiffIndex = 0;

        while (start + interval - 1 < minutes) {
            int currentDiff = Math.abs(readings[start] - readings[start+ interval - 1]);
            if (currentDiff > maxDiff) {
                maxDiff = currentDiff;
                maxDiffIndex = start;
            }
            start++;
        }

        for (int i = 0; i < interval; ++i) {
            tempDiffInterval[i] = readings[maxDiffIndex+i];
        }
    }

    void displayData() {
        System.out.println("Diff interval");
        for (int i = 0; i < interval; ++i) System.out.println(tempDiffInterval[i]);
        System.out.println("top 5");
        for (int i = 0; i < 5; ++i) System.out.println(top5[i]);
        System.out.println("min 5");
        for (int i = 0; i < 5; ++i) System.out.println(min5[i]);
    }
}
