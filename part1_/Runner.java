class Runner {

    static final int n_gifts = 500000;
    static final int n_servants = 4;
    static GiftNode[] gifts = new GiftNode[n_gifts];
    
    public static void main(String[] args) {
        
        for (int i = 0; i < n_gifts; ++i) {
            gifts[i] = new GiftNode(i);
        }

        
    }
}