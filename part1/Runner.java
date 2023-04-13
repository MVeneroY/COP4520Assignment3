import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Runner {

    static final int n_gifts = 500_000;
    static final int n_servants = 4;
    static GiftNode[] gifts = new GiftNode[n_gifts];
    static ServantThread[] servants = new ServantThread[n_servants];
    static int[] servant_tasks = new int[n_servants];
    
    static Random rand = new Random();

    public static void main(String[] args) {
        
        // Initialize gifts, bag and list
        for (int i = 0; i < n_gifts; ++i) gifts[i] = new GiftNode(i);
        for (int i = 0; i < n_gifts; ++i) swap(i, rand.nextInt(n_gifts));
        GiftList list = new GiftList();
        
        // Initialize servants
        ExecutorService executorService = Executors.newFixedThreadPool(n_servants);
        for (int i = 0; i < n_servants; ++i) {
            servants[i] = new ServantThread(i, n_gifts, n_servants, gifts, list);
            servant_tasks[i] = i;
        }

        long start = System.currentTimeMillis();

        int taskCount = 0;
        while (!allInLocation(GiftNode.Location.out)) {
            // servants carry out tasks
            // try {
            //     Thread.sleep(10);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }

            executorService.execute(servants[taskCount % n_servants]);   
            servants[taskCount % n_servants].updateTask(servant_tasks[taskCount % n_servants]);
            servant_tasks[taskCount % n_servants] += 1;
            taskCount += 1;
        }

        // System.out.println("Chain is in order: " + list.verifyContinuity());
        long end = System.currentTimeMillis();

        System.out.println("Total time: " + (end - start) + "ms");

        System.err.println("Shutting down executor service...");
        executorService.shutdown();
        while (!executorService.isTerminated()) { 
            try {
                Thread.sleep(10);
                // System.out.println("shut down check...");
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        list = null;
        gifts = null;
    }

    public static void swap(int index1, int index2) {
        GiftNode temp = gifts[index1];
        gifts[index1] = gifts[index2];
        gifts[index2] = temp;
    }

    public static boolean allInLocation(GiftNode.Location location) {
        return gifts[n_gifts-1].getLocation() == location;
    }
}