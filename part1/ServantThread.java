public class ServantThread implements Runnable {

    private int task;
    int n_gifts;
    int n_servants;
    GiftNode[] gifts;
    GiftList list;

    ServantThread(int task, int n_gifts, int n_servants, GiftNode[] gifts, GiftList list) {
        this.task = task;
        this.n_gifts = n_gifts;
        this.n_servants = n_servants;
        this.gifts = gifts;
        this.list = list;
    }

    // TODO: implement locks
    @Override
    public void run() {
        switch (task % 3) {
            case 1:
            // Add gift to list
            addGiftToChain(list);
            break;
            case 2:
            // Write thank you note to gift
            break;
            case 3:
            // Search for gift in list
            break;
            default:
        }
    }
    
    public void updateTask(int task) { this.task = task; }

    // Add to list
    void addGiftToChain(GiftList list) {
        GiftNode gift = findNextGift();
        if (gift != null) list.add(gift);
    }

    GiftNode findNextGift() {
        int tag = list.getNextTag();
        for (int i = 0; i < n_gifts; ++i) {
            if (gifts[i].getTag() == tag) return gifts[i];
        }

        return null;
    }

    // Write thank you note
    // Search for gift
}
