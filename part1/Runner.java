import java.util.Random;

class Runner {

    static final int n_gifts = 500000;
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
        for (int i = 0; i < n_servants; ++i) {
            servants[i] = new ServantThread(0, n_gifts, n_servants, gifts, list);
            servant_tasks[i] = 0;
        }

        // Will continue working in a bit
        while (!allOut()) {
            // servants carry out tasks
        }
    }

    public static void swap(int index1, int index2) {
        GiftNode temp = gifts[index1];
        gifts[index1] = gifts[index2];
        gifts[index2] = temp;
    }

    public static boolean allOut() {
        for (int i = 0; i < n_gifts; ++i) {
            if (gifts[i].getLocation() != GiftNode.Location.out) return false;
        }

        return true;
    }
}