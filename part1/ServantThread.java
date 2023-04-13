import java.util.Random;

public class ServantThread implements Runnable {

    private int task;
    int n_gifts;
    int n_servants;
    GiftNode[] gifts;
    GiftList list;

    Random rand;

    ServantThread(int task, int n_gifts, int n_servants, GiftNode[] gifts, GiftList list) {
        this.task = task;
        this.n_gifts = n_gifts;
        this.n_servants = n_servants;
        this.gifts = gifts;
        this.list = list;

        rand = new Random();
    }

    @Override
    public void run() {
        // No need to add gifts if all gifts are in the chain
        if (list.tail != null) {
            if (task % 3 == 0 && list.tail.getLocation() == GiftNode.Location.list) task = 1;
        }

        switch (task % 3) {
            case 0:
            // Add gift to list
            // System.out.println("Adding gift to chain...");
            addGiftToChain(list);
            break;
            case 1:
            // Write thank you note to gift
            GiftNode gift = list.pop();
            writeNote(gift);
            break;
            case 2:
            // Search for gift in list
            int tag = rand.nextInt(n_gifts);
            boolean result = list.searchNode(tag);
            // if (result) System.out.println("gift #" + tag + "present in list");
            // else System.out.println("present #" + tag + " not present in list");
            break;
            default:
        }

        // int tag = list.clean();
        // if (tag != -1) gifts[tag] = null;
    }
    
    public void updateTask(int task) { this.task = task; }

    // Add to list
    void addGiftToChain(GiftList list) {
        GiftNode gift = findNextGift();
        if (gift == null) return;

        // System.out.println("Adding gift with id:" + gift.getTag());
        list.add(gift);
    }

    GiftNode findNextGift() {
        int tag = list.getNextTag();
        if (tag >= n_gifts) return null;
        for (int i = 0; i < n_gifts; ++i) {
            if (gifts[i].getTag() == tag) return gifts[i];
        }

        return null;
    }

    // Write thank you note
    void writeNote(GiftNode gift) {
        if (gift == null) return;

        // System.out.println("Writing thank you note to gift with id: " + gift.getTag());
        gift.setCard('t');
        gift.setLocation(GiftNode.Location.out);
    }
}
