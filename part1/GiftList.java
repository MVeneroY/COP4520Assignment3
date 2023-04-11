import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GiftList {

    GiftNode head = null;
    Lock lock;
    // int lastTag = -1;
    // int nextTag;
    // boolean started = false;

    // Current implementation: just locks
    // cores choke in add operation

    GiftList() { this.lock = new ReentrantLock(); }
    GiftList(GiftNode head) { 
        this.head = head; 
        this.lock = new ReentrantLock();
    }

    public boolean isEmpty() { return head == null; }

    public void display() {
        if (head == null) return;

        GiftNode curr = head;
        while (curr != null) {
            System.out.printf("%d -> ", curr.getTag());
            curr = curr.next;
        }

        System.out.println("X");
    }

    public void add(GiftNode node) {
        if (node == null || node.getLocation() != GiftNode.Location.bag) return;
        // started = true;

        lock.lock();
        try {
            if (this.isEmpty()) {
                this.head = node;
                node.next = null;
                node.prev = null;
                node.setLocation(GiftNode.Location.list);
                // nextTag = node.getTag();
                return;
            }

            GiftNode curr = head;
            while (curr.next != null) { curr = curr.next; }
            curr.next = node;
            node.next = null;
            node.prev = curr;
            node.setLocation(GiftNode.Location.list);
        } finally { lock.unlock(); }
        // nextTag = node.getTag();
    }

    public int getNextTag() {
        // if (!started) return 0;
        // return nextTag;
        if (this.isEmpty()) return 0;
        GiftNode curr = head;

        while (curr.next != null) curr = curr.next;
        return curr.getTag() + 1; 
    } 

    // removes the head from the list and returns it
    public GiftNode pop() {
        if (this.isEmpty()) return null;

        GiftNode curr = this.head;
        if (curr.next == null) {
            this.head = null;
            curr.setLocation(GiftNode.Location.out);
            return curr;
        }

        this.head = this.head.next;
        this.head.prev = null;

        curr.setLocation(GiftNode.Location.out);
        return curr;
    }

    // Verify that all nodes are in immediate ascending order
    public boolean verifyContinuity() {
        if (this.isEmpty()) return true;

        GiftNode prev = this.head;
        GiftNode curr = prev.next;
        while (curr != null) {
            if (prev.getTag() + 1 != curr.getTag()) return false;
            prev = curr;
            curr = curr.next;
        }

        return true;
    }
}