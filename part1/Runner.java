import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Runner {

    static final int n_gifts = 5000;
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
            servants[i] = new ServantThread(0, n_gifts, n_servants, gifts, list);
            servant_tasks[i] = 0;
        }

        int taskCount = 0;
        // Will continue working in a bit
        while (!allInLocation(GiftNode.Location.list)) {
            // servants carry out tasks
            executorService.execute(servants[taskCount % n_servants]);   
            servants[taskCount % n_servants].updateTask(servant_tasks[taskCount % n_servants]);
            servant_tasks[taskCount % n_servants] += 1;
            taskCount += 1;
        }

        list.display();
        System.out.println("Chain is in order: " + list.verifyContinuity());

        executorService.shutdown();
        while (!executorService.isTerminated()) { 
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public static void swap(int index1, int index2) {
        GiftNode temp = gifts[index1];
        gifts[index1] = gifts[index2];
        gifts[index2] = temp;
    }

    public static boolean allInLocation(GiftNode.Location location) {
        for (int i = 0; i < n_gifts; ++i) {
            if (gifts[i].getLocation() != location) return false;
        }

        return true;
    }
}