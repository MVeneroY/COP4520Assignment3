import java.util.Random;

class Runner {

    static final int n_gifts = 500000;
    static final int n_servants = 4;
    static GiftNode[] gifts = new GiftNode[n_gifts];
    
    static Random rand = new Random();

    public static void main(String[] args) {
        
        // Initialize gifts, bag and list
        for (int i = 0; i < n_gifts; ++i) gifts[i] = new GiftNode(i);
        for (int i = 0; i < n_gifts; ++i) swap(i, rand.nextInt(n_gifts));
        GiftList list = new GiftList();
        
        // Will continue working in a bit
    }

    public static void swap(int index1, int index2) {
        GiftNode temp = gifts[index1];
        gifts[index1] = gifts[index2];
        gifts[index2] = temp;
    }
}