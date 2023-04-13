import java.util.Random;
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

        mergeSort(readings, minutes);
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

    // Arrays.sort() was returning "Index -1 out of bounds" so here's mergeSort()
    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
    
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);
    
        merge(a, l, r, mid, n - mid);
    }

    public static void merge(int[] a, int[] l, int[] r, int left, int right) {
    
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
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
