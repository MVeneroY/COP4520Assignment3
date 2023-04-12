import java.util.Random;
import java.util.Arrays;
import java.lang.Math;

public class SensorThread implements Runnable {
    
    Random rand;
    final int min = -100;
    final int max = 70;
    final int minutes = 60;
    final int interval = 10;

    int[] readings;
    int[] top5;
    int[] min5;
    int[] temperatureDifferenceInterval;

    SensorThread() {
        rand = new Random();
        readings = new int[minutes];
    }

    @Override
    public void run() {
        for (int i = 0; i < minutes; ++i) {
            readings[i] = getTemperatureReading();
        }
        temperatureDifferenceInterval = getLargestTempDifferenceInterval();

        Arrays.sort(readings);
        top5 = getHighestTemperatures();
        min5 = getLowestTemperatures();

        System.out.println("Diff interval");
        for (int i = 0; i < interval; ++i) System.out.println(temperatureDifferenceInterval[i]);
        System.out.println("top 5");
        for (int i = 0; i < 5; ++i) System.out.println(top5[i]);
        System.out.println("min 5");
        for (int i = 0; i < 5; ++i) System.out.println(min5[i]);
    }

    int getTemperatureReading() {
        int abs = rand.nextInt(max - min + 1);
        return abs + min;
    }

    // Precondition: readings array is sorted
    int[] getHighestTemperatures() {
        int[] top5 = new int[5];
        for (int i = 0; i < 5; ++i) top5[i] = readings[minutes-5+i];

        return top5;
    }

    // Precondition: readings array is sorted
    int[] getLowestTemperatures() {
        int[] min5 = new int[5];    
        for (int i = 0; i < 5; ++i) min5[i] = readings[i];

        return min5;
    }

    // Precondition: readings are in the order they were recorded in
    int[] getLargestTempDifferenceInterval() {
        int[] temperatureDifferenceInterval = new int[interval];
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
            temperatureDifferenceInterval[i] = readings[maxDiffIndex+i];
        }

        return temperatureDifferenceInterval;
    }
}
